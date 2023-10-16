package success.navigator.website.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import success.navigator.website.entities.Category;
import success.navigator.website.repositories.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void add(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> getCategoriesList() {
        return categoryRepository.findAll();
    }

    public Category getById(Long id) {
        return categoryRepository.findById(id).orElse(new Category());
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
