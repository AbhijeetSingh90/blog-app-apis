package blogappapis.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import blogappapis.entity.Category;
import blogappapis.entity.Post;
import blogappapis.entity.User;
import blogappapis.exception.ResourceNotFoundException;
import blogappapis.payload.PostDto;
import blogappapis.payload.PostResponse;
import blogappapis.repositeries.CategoryResp;
import blogappapis.repositeries.PostRepo;
import blogappapis.repositeries.UserRepository;
import blogappapis.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper; 
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryResp categoryResp;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
	
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "userId", userId));
		
		Category category = this.categoryResp.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category", "categoryId", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
	    post.setImageName("default.png");
	    post.setAddedDate(new Date());
	    post.setUser(user);
	    post.setCategory(category);
	    Post newPost = this.postRepo.save(post);
	    
	    return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "PostId", postId));
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		post.setTitle(postDto.getTitle());
		Post save = this.postRepo.save(post);
		return this.modelMapper.map(save, PostDto.class);
		}

	@Override
	public void deletePost(Integer postId) {
	   Post post = this.postRepo.findById(postId)
			   .orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
	   this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> allPost = pagePost.getContent();
		
		List<PostDto> lists = allPost.stream().map((all)->this.modelMapper.map(all, PostDto.class))
				.collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(lists);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "PostId", postId)); 
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category cat = this.categoryResp.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("category", "categoryId" , categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);
		List<PostDto> postDtos= posts.stream().map((post)->this.modelMapper.map(post,PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User users = this.userRepository.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("User", "UserId", userId));
		List<Post> posts = this.postRepo.findByUser(users);
	    List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class))
	    		.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> lists = this.postRepo.searchByTitle("%"+keyword+"%");
		List<PostDto> collect = lists.stream()
				.map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return collect;
	}

	

}
