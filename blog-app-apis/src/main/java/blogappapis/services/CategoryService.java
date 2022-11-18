package blogappapis.services;

import java.util.List;

import blogappapis.payload.CategoryDto;

public interface CategoryService {
	
	//Create 
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//Update
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	//Delete
    void deleteCategory(Integer categoryId);
	
	//GEt All
	List<CategoryDto> getCategory();
	
	//Get By Id
	public CategoryDto getCategory(Integer categoryId);

}
