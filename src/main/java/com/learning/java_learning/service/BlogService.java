package com.learning.java_learning.service;

import com.learning.java_learning.dto.request.BlogRequest;
import com.learning.java_learning.entity.Blog;
import com.learning.java_learning.exception.CustomExceptions;
import com.learning.java_learning.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Blog getBlogById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Blog not found with id: " + id));
    }

    public Blog createBlog(BlogRequest blogRequest) {
        Blog blog = new Blog();
        blog.setTitle(blogRequest.getTitle());
        blog.setContent(blogRequest.getContent());
        blog.setAuthor(blogRequest.getAuthor());
        return blogRepository.save(blog);
    }

    public Blog updateBlog(Long id, BlogRequest blogRequest) {
        Blog existingBlog = getBlogById(id);
        existingBlog.setTitle(blogRequest.getTitle());
        existingBlog.setContent(blogRequest.getContent());
        existingBlog.setAuthor(blogRequest.getAuthor());
        return blogRepository.save(existingBlog);
    }

    public void deleteBlog(Long id) {
        Blog existingBlog = getBlogById(id);
        blogRepository.delete(existingBlog);
    }
} 