package com.jwt.services;

import java.util.List;

import com.jwt.dto.PostDTO;

public interface PostService {

	List<PostDTO> getAllPosts();

	PostDTO createNewPost(PostDTO inputPost);

	PostDTO getPostById(Long postId);
}
