	package com.aTut.blog.services.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aTut.blog.Payload.CategoryDto;
import com.aTut.blog.entities.Category;
import com.aTut.blog.exceptions.ResourceNotFoundException;
import com.aTut.blog.repositories.CategoryRepository;
import com.aTut.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired 
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		
		Category  cat = this.modelMapper.map(categoryDto, Category.class);
		Category added_category = this.categoryRepo.save(cat);
		return this.modelMapper.map(added_category,CategoryDto.class);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "CategoryId", categoryId));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> catList = this.categoryRepo.findAll();
		List<CategoryDto> allCategory = catList.stream()
				.map((category)->this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return allCategory;
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto) {
		Category cat = this.categoryRepo.findById(categoryDto.getCategoryId())
				.orElseThrow(()->new ResourceNotFoundException("Category", "CategoryId", categoryDto.getCategoryId()));
		cat.setCategoryId(categoryDto.getCategoryId());
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
				 Category updatedCategory = this.categoryRepo.save(cat);
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "CategoryId", categoryId));
		this.categoryRepo.delete(cat);
	}
	
	
	
	
	

}
