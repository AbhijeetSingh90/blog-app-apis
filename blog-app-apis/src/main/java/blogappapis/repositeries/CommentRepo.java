package blogappapis.repositeries;

import org.springframework.data.jpa.repository.JpaRepository;

import blogappapis.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>  {

}
