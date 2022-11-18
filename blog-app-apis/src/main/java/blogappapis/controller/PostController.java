package blogappapis.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import blogappapis.config.AppConstants;
import blogappapis.payload.ApiRes;
import blogappapis.payload.PostDto;
import blogappapis.payload.PostResponse;
import blogappapis.services.FileService;
import blogappapis.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	// create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}

	// Get by User
	@GetMapping("/user/{userId}/post")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {
		List<PostDto> list = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(list, HttpStatus.OK);
	}

	// Get by Category
	@GetMapping("/category/{categoryId}/post")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
		List<PostDto> list = this.postService.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(list, HttpStatus.OK);
	}
	
	//Get All Post
	@GetMapping("/")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir){
		  PostResponse allPost = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
	}
	
	//Get By Id
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPostByID(@PathVariable Integer postId){
		PostDto byId = this.postService.getPostById(postId);
		System.out.println(byId);
		return new ResponseEntity<PostDto>( byId , HttpStatus.OK );
		
	}
	
	//Delete Post
	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiRes> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiRes>(new ApiRes("Post Deleted Successfully",true),HttpStatus.OK);
	}
	
	// Update 
	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable Integer postId){
		PostDto post = this.postService.updatePost(postDto,postId);
		return new ResponseEntity<PostDto>(post,HttpStatus.OK);
	}
	
	//Search
	@GetMapping("/post/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(
			@PathVariable("keywords") String keywords){
		List<PostDto> searchPosts = this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(searchPosts,HttpStatus.OK);		
		
	}
	
	//Post images
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image
			,@PathVariable Integer postId) throws IOException{
		
		PostDto postById = this.postService.getPostById(postId);
		String uploadImage = this.fileService.uploadImage(path, image);	
		postById.setImageName(uploadImage);
		PostDto updatePost = this.postService.updatePost(postById, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
		
	}
	
	@GetMapping(value="/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable ("imageName") String imageName,HttpServletResponse response)throws IOException {
		
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

}
