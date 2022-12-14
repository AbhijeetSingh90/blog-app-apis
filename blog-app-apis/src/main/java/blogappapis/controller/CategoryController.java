package blogappapis.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blogappapis.payload.ApiRes;
import blogappapis.payload.CategoryDto;
import blogappapis.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {		

	@Autowired
	private CategoryService categoryService;

	// Create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) 
	{
		CategoryDto categoryDto1 = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(categoryDto1, HttpStatus.CREATED);
	}
	

	// Update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId) {
		CategoryDto categoryDto1 = this.categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(categoryDto1, HttpStatus.OK);
	}

	// Detete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiRes> deleteCategory(@PathVariable Integer categoryId) {
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiRes>(new ApiRes("Category Deleted Successfully", true), HttpStatus.OK);
	}

	// Get
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId) {
		CategoryDto category = this.categoryService.getCategory(categoryId);
		return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
	}
	
	// GetAll
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategory() {
		List<CategoryDto> list = this.categoryService.getCategory();
		return ResponseEntity.ok(list);
	}

}
