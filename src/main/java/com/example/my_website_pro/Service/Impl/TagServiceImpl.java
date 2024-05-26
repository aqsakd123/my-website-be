package com.example.my_website_pro.Service.Impl;

import com.example.my_website_pro.Config.AppException;
import com.example.my_website_pro.Entity.DTO.TagDTO;
import com.example.my_website_pro.Entity.DTO.RequestDTO.TagSpecificationDTO;
import com.example.my_website_pro.Entity.DTO.UserDTO;
import com.example.my_website_pro.Entity.Tag;
import com.example.my_website_pro.Entity.Mapper.TagMapper;
import com.example.my_website_pro.Repository.TagRepository;
import com.example.my_website_pro.Service.TagService;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    private final TagMapper tagMapper = Mappers.getMapper(TagMapper.class);

    @Override
    public TagDTO getDetailTag(String id) {
        Tag res = tagRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.UNAUTHORIZED));
        return tagMapper.toDTO(res);
    }

    @Override
    public Tag insertTag(TagDTO tag) {
        tag.setStatus(1);
        return tagRepository.save(tagMapper.toEntity(tag));
    }

    @Override
    public Tag updateTag(String id, TagDTO tag) {
        Tag value = tagRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.UNAUTHORIZED));
        value.setName(tag.getName());
        value.setColor(tag.getColor());
        value.setIcon(tag.getIcon());
        value.setDescription(tag.getDescription());
        return tagRepository.save(value);
    }

    @Override
    public Tag changeStatus(String id, Integer status) {
        Tag savetag = tagRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.UNAUTHORIZED));
        savetag.setStatus(status);
        return tagRepository.save(savetag);
    }

    @Override
    public Tag deleteTag(String id) {
        Tag res = tagRepository.findById(id).orElseThrow(() -> new AppException("UNKNOWN_ID", HttpStatus.UNAUTHORIZED));
        res.setStatus(0);
        return tagRepository.save(res);
    }

    @Override
    public List<TagDTO> getListTag(TagSpecificationDTO specification) {
        Specification<Tag> spec = getSpecification(specification);
        return tagMapper.toListDTO(tagRepository.findAll(spec));
    }

    @Override
    public Page<TagDTO> getPageTag(TagSpecificationDTO specification) {
        int pageSize = specification.getPageSize() != null ? specification.getPageSize() : 1;
        int pageIndex = specification.getPageIndex() != null ? specification.getPageIndex() : 100;

        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        Specification<Tag> spec = getSpecification(specification);
        Page<Tag> tagPage = tagRepository.findAll(spec, pageable);

        return mappingPageTagToPageTagDTO(tagPage);
    }

    @Override
    public Integer countTagByFilter(TagSpecificationDTO specification) {
        Specification<Tag> spec = getSpecification(specification);
        return Math.toIntExact(tagRepository.count(spec));

    }

    @Override
    public void deleteAllByFilter(TagSpecificationDTO specification) {

        Specification<Tag> spec = getSpecification(specification);
        List<Tag> tagsToDelete = tagRepository.findAll(spec);

        for (Tag tag : tagsToDelete) {
            tag.setStatus(0);
            tagRepository.save(tag);
        }
    }

    public static Specification<Tag> withLargestPosition() {
        return (root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.desc(root.get("position")));
            query.distinct(true);
            return criteriaBuilder.equal(root.get("position"), root.get("position"));
        };
    }

    private Specification<Tag> getSpecification(TagSpecificationDTO specification) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Authentication authen = SecurityContextHolder.getContext().getAuthentication();
            UserDTO user = (UserDTO) authen.getPrincipal();

            predicates.add(criteriaBuilder.notEqual(root.get("status"), 0));
            predicates.add(criteriaBuilder.equal(root.get("createdBy"), user.getUsername()));
            predicates.add(criteriaBuilder.equal(root.get("type"), specification.getType()));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Page<TagDTO> mappingPageTagToPageTagDTO(Page<Tag> tagPage) {
        // Help mapping Page to Page DTO without queryN+1
        return tagPage.map(tagMapper::toDTO);
    }
}
