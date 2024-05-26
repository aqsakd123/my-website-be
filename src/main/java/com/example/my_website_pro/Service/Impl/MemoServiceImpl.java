package com.example.my_website_pro.Service.Impl;

import com.example.my_website_pro.Config.AppException;
import com.example.my_website_pro.Entity.DTO.MemoDTO;
import com.example.my_website_pro.Entity.DTO.RequestDTO.MemoSpecificationDTO;
import com.example.my_website_pro.Entity.DTO.TagDTO;
import com.example.my_website_pro.Entity.DTO.UserDTO;
import com.example.my_website_pro.Entity.Mapper.MemoMapper;
import com.example.my_website_pro.Entity.Mapper.QuesAndAnsMapper;
import com.example.my_website_pro.Entity.Mapper.TabMapper;
import com.example.my_website_pro.Entity.Mapper.TagMapper;
import com.example.my_website_pro.Entity.Memo;
import com.example.my_website_pro.Entity.Tag;
import com.example.my_website_pro.Repository.MemoRepository;
import com.example.my_website_pro.Service.MemoService;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class MemoServiceImpl implements MemoService {
    @Autowired
    private MemoRepository memoRepository;

    private final MemoMapper memoMapper = Mappers.getMapper(MemoMapper.class);
    private final TabMapper tabMapper = Mappers.getMapper(TabMapper.class);

    private final QuesAndAnsMapper qaMapper = Mappers.getMapper(QuesAndAnsMapper.class);
    private final TagMapper tagMapper = Mappers.getMapper(TagMapper.class);

    @Override
    public MemoDTO getDetailMemo(String id) {
        Memo res = memoRepository.findById(id)
                .orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.UNAUTHORIZED));

        return memoMapper.toDTO(res);
    }

    @Override
    public Memo insertMemo(MemoDTO memo) {
        memo.setStatus(1);
        if(memo.getPosition() == null) {
            memo.setPosition(getLargestPosition());
        }
        return memoRepository.save(memoMapper.toEntity(memo));
    }

    @Override
    public Memo updateMemo(String id, MemoDTO memo) {
        Memo value = memoRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.UNAUTHORIZED));

        value.setName(memo.getName());
        value.setStatus(memo.getStatus());
        value.setColor(memo.getColor());
        value.setTabCardList(tabMapper.toListEntities(memo.getTabCardList()));
        value.setQaList(qaMapper.toListEntities(memo.getQaList()));
        value.setTags(tagMapper.toListEntities(memo.getTags()));

        return memoRepository.save(value);
    }

    @Override
    public Memo changeStatus(String id, Integer status) {
        Memo savememo = memoRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.UNAUTHORIZED));
        savememo.setStatus(status);
        return memoRepository.save(savememo);
    }

    @Override
    public Memo deleteMemo(String id) {
        Memo res = memoRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.UNAUTHORIZED));
        res.setStatus(0);
        return memoRepository.save(res);
    }

    @Override
    public List<Memo> getListMemo(MemoSpecificationDTO specification) {
        Specification<Memo> spec = getSpecification(specification);
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        return memoRepository.findAll(spec, sort).stream().toList();
    }

    public Double getLargestPosition() {
        MemoSpecificationDTO specification = new MemoSpecificationDTO();
        Specification<Memo> spec = getSpecification(specification);
        Sort sort = Sort.by(Sort.Direction.DESC, "position");
        Page<Memo> page = memoRepository.findAll(spec, PageRequest.of(0, 1, sort));
        Double pos = null;
        if (page.hasContent()) {
            pos = page.getContent().get(0).getPosition();
        }
        if (pos != null) {
            return pos + 1.0;
        }
        return (double) 0;
    }

    @Override
    public Page<MemoDTO> getPageMemo(MemoSpecificationDTO specification) {
        int pageSize = specification.getPageSize() != null ? specification.getPageSize() : 1;
        int pageIndex = specification.getPageIndex() != null ? specification.getPageIndex() : 100;

        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        Specification<Memo> spec = getSpecification(specification);
        Page<Memo> memoPage = memoRepository.findAll(spec, pageable);
        return mappingPageMemoToPageMemoDTO(memoPage);
    }

    @Override
    public Integer countMemoByFilter(MemoSpecificationDTO specification) {
        Specification<Memo> spec = getSpecification(specification);
        return Math.toIntExact(memoRepository.count(spec));

    }

    @Override
    public void deleteAllByFilter(MemoSpecificationDTO specification) {

        Specification<Memo> spec = getSpecification(specification);
        List<Memo> memosToDelete = memoRepository.findAll(spec);

        for (Memo memo : memosToDelete) {
            memo.setStatus(0);
            memoRepository.save(memo);
        }
    }

    private Specification<Memo> getSpecification(MemoSpecificationDTO specification) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Authentication authen = SecurityContextHolder.getContext().getAuthentication();
            UserDTO user = (UserDTO) authen.getPrincipal();

            predicates.add(criteriaBuilder.notEqual(root.get("status"), 0));
            predicates.add(criteriaBuilder.equal(root.get("createdBy"), user.getUsername()));

            if (StringUtils.hasLength(specification.getType())) {
                predicates.add(criteriaBuilder.equal(root.get("type"), specification.getType()));
            }
            if (specification.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), specification.getStatus()));
            }
            if (StringUtils.hasLength(specification.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + specification.getName() + "%"));
            }
            if (Objects.nonNull(specification.getCategory()) && StringUtils.hasLength(specification.getCategory().getId())) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), specification.getCategory().getId()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Page<MemoDTO> mappingPageMemoToPageMemoDTO(Page<Memo> memoPage) {
        // Help mapping Page to Page DTO without queryN+1
        return memoPage.map(memoMapper::toDTO);
    }

}
