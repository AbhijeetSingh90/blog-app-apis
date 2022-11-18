	package blogappapis.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blogappapis.entity.Comment;
import blogappapis.entity.Post;
import blogappapis.exception.ResourceNotFoundException;
import blogappapis.payload.CommentDto;
import blogappapis.repositeries.CommentRepo;
import blogappapis.repositeries.PostRepo;
import blogappapis.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("post", "postId", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment save1 = this.commentRepo.save(comment);
		
		return this.modelMapper.map(save1, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment com = this.commentRepo.findById(commentId)
				.orElseThrow(()->new ResourceNotFoundException("comment", "commentId", commentId)); 
		this.commentRepo.delete(com);
	}

}
