package org.boot.post_springboot.demo.service;

import org.boot.post_springboot.demo.domain.Categories;
import org.boot.post_springboot.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    // 카테고리 객체를 가져와서 쓰겠다는 의미~

    // 레파지토리는 쿼리만 작성 자세한 요구사항
    // ex) 유저가 로그인을 했다면 or 글을 작성한 유저라면
    // 이 부분은 관리자가 추가되면 진행 일단은 임의의 카테고리 db에서 추가하여 진행

    // 카테고리 추가 기능
    public Categories saveCategory(Categories category) {
        return categoryRepository.save(category);
    }

    // 카테고리 전체 조회 기능(카테고리 별 보기 시 필요)
    public List<Categories> getAllCategories() {
        return categoryRepository.findAll();
    }
    // 카테고리 별 조회 기능
    public Optional<Categories> getCategoryById(UUID cateId) {
        return categoryRepository.findById(cateId);
    }
    // 카테고리 삭제 기능
    public void deleteCategoryById(UUID cateId) {
        categoryRepository.deleteById(cateId);
    }
    // 카테고리 수정 기능
    public Categories updateCategory(Categories category) {
        return categoryRepository.save(category);
    }

    public Categories findByCateId(UUID cateId) {
        return categoryRepository.findById(cateId).get();
    }

}