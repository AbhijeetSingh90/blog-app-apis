package blogappapis.repositeries;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import blogappapis.entity.Category;
import blogappapis.entity.Post;
import blogappapis.entity.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User userId);
	List<Post> findByCategory(Category categoryId);
	
	@Query("select p from Post p where p.title like :key")
	List<Post> searchByTitle(@Param("key") String title);
	
}
