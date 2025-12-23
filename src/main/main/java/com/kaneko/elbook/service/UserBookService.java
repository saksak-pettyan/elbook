package com.kaneko.elbook.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kaneko.elbook.domain.Book;
import com.kaneko.elbook.mapper.BookMapper;

@Service
public class UserBookService {

	private final BookMapper bookMapper;

	public UserBookService(BookMapper bookMapper) {
		this.bookMapper = bookMapper;
	}

	/** タイトル部分一致検索（null なら全件） */
	public List<Book> searchBooks(String title) {
		if (title == null || title.isBlank()) {
			return bookMapper.findAll();
		}
		return bookMapper.findByTitleLike(title);
	}

	/** 書籍1件取得 */
	public Book getBook(Long bookId) {
		return bookMapper.findById(bookId);
	}
}
