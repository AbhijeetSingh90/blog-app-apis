package blogappapis.services;

import java.util.List;

import blogappapis.payload.PostDto;
import blogappapis.payload.PostResponse;

public interface PostService {

	//Create
	PostDto createPost(PostDto postDto , Integer userId,Integer categoryId);
	
	//Update
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//Delete
	void deletePost(Integer postId);
	
	//Get all posts
	PostResponse getAllPost( Integer pageNumber , Integer pageSize , String sortBy ,String sortDir);
	
	//Get Single post
	PostDto getPostById(Integer postId);

	//Get all post by category
	List<PostDto> getPostByCategory(Integer categoryId);
	
	//Get all posts by user
	List<PostDto> getPostByUser(Integer userId);
	
	//Search
	List<PostDto> searchPosts(String keyword);
}
