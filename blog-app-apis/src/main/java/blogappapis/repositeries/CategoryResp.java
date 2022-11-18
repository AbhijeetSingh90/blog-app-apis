package blogappapis.repositeries;

import org.springframework.data.jpa.repository.JpaRepository;

import blogappapis.entity.Category;

public interface CategoryResp extends JpaRepository<Category, Integer> {
	
	
}
