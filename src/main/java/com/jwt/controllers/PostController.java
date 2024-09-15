package com.jwt.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.dto.PostDTO;
import com.jwt.services.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@GetMapping
	public List<PostDTO> getAllPosts() {
		return postService.getAllPosts();
	}

	@GetMapping("/{postId}")
	public PostDTO getPostById(@PathVariable final Long postId) {
		return postService.getPostById(postId);
	}

	@PostMapping
	public PostDTO createNewPost(@RequestBody final PostDTO inputPost) {
		return postService.createNewPost(inputPost);
	}

}
