package com.kaneko.elbook.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kaneko.elbook.domain.Book;
import com.kaneko.elbook.form.AdminBookForm;
import com.kaneko.elbook.mapper.BookMapper;

/**
 * 管理画面用の書籍一覧サービス。
 * コントローラから呼び出され、検索条件に応じて Mapper を使い分けます。
 */
@Service
public class AdminBookService {

	private final BookMapper bookMapper;

	public AdminBookService(BookMapper bookMapper) {
		this.bookMapper = bookMapper;
	}
	
	/**
     * 書籍一覧検索。
     * 検索条件が空なら全件、指定されていれば部分一致検索を行います。
     */
	public List<Book> searchBooks(String searchBookName) {
		
		if (searchBookName==null||searchBookName.isBlank()) {
			// 検索条件なし → 全件取得
			return bookMapper.findAll();
		} else {
			// 部分一致で検索
			return bookMapper.findByTitleLike(searchBookName.trim());
		}
	}
	
	/**
	 *  書籍ID で１件取得（詳細表示用）。
	 */
	public Book getBook(Long bookId) {
		if (bookId ==null) {
			return null;
		}
		return bookMapper.findById(bookId);
	}
	
	/**
     * 書籍新規登録。
     * 総冊数 = 在庫冊数、貸出冊数 = 0 で登録します。
     */
	public void registerNewBook(AdminBookForm form) {
		Book book = new Book();
		book.setTitle(form.getBookName());
		book.setAuthor(form.getAuthorName());
		book.setLocation(form.getLocation());
		book.setTotalCount(form.getTotalCount());
		// 新規なので貸出冊数は 0、在庫 = 総冊数
		book.setRentalCount(0);
		book.setStockCount(form.getTotalCount());
		
		bookMapper.insert(book);
	}
	
	/**
     * 書籍情報更新。
     * 総冊数・基本情報のみ更新し、在庫・貸出冊数は貸出処理で管理する想定。
     */
	public void updateBook(AdminBookForm form) {
		Book book = new Book();
	    book.setBookId(form.getBookId());
	    book.setTitle(form.getBookName());
	    book.setAuthor(form.getAuthorName());
	    book.setLocation(form.getLocation());
	    book.setTotalCount(form.getTotalCount());
	    
	    int updatede=bookMapper.update(book);
	    if(updatede !=1) {
	    	throw new IllegalStateException("更新に失敗しました（総冊数が貸出冊数未満の可能性があります）。");
	    }
	}
}
