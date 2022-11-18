package blogappapis.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blogappapis.entity.Category;
import blogappapis.exception.ResourceNotFoundException;
import blogappapis.payload.CategoryDto;
import blogappapis.repositeries.CategoryResp;
import blogappapis.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryResp categoryResp;

	@Autowired
	private ModelMapper modelMapper;

	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category addedCat = this.categoryResp.save(cat);
		return this.modelMapper.map(addedCat,CategoryDto.class);
	}	
	
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat = this.categoryResp.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category save = this.categoryResp.save(cat);
		return this.modelMapper.map(save, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.categoryResp.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		this.categoryResp.delete(cat);
	}

	@Override
	public List<CategoryDto> getCategory() {
		List<Category> list = this.categoryResp.findAll();
		List<CategoryDto> collect = list.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat = this.categoryResp.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Categoty", "CategoryId", categoryId));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

}
