package com.biz.book.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.biz.book.domain.BookVO;
import com.biz.book.persistence.BookRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("bookServiceV1")
public class BookServiceImplV1 implements BookService{

    private final BookRepository bookDao;

    public BookServiceImplV1(BookRepository bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public List<BookVO> selectAll() {
        List<BookVO> bookList = bookDao.findAll();
        return bookList;
    }

    /*
    Optional
    데이터가 없음을 null이 아닌 어떤 명확한 근거를 바탕으로 알고싶다! 라는 취지에서 새로 생성한 wrapper class
    VO클래스를 감싸는 wrapper class

    일반적으로 DB핸들링에서 findById(Long id)를 수행한 후
    id에 해당하는 데이터가 없을 경우 return 값이 null 이었다.

    if(vo == null) {
        log.debug("데이터가 없음");
    }

    optional은 어떤 객체의 저장된 값이 null인지 비교하는 것을 사용하지 않기 위해서 새롭게 등장한 class

    null값을 비교하는 것보다 연산비용이 많이 든다
    아직은 논란의 여지가 많다.
     */
    @Override
    public BookVO findById(Long id) {
        Optional<BookVO> bookVO = bookDao.findById(id);
        
        // 만약 findById()를 수행하여 데이터가 없으면
        // 새로운 VO를 만들고 id값을 -1로 Setting 하여 return하라!
//        return bookVO.orElse(BookVO.builder().id(-1L).build());
        return bookVO.get();
    }

    @Override
    public int insert(BookVO bookVO) {
        bookDao.save(bookVO);
        return 0;
    }

    @Override
    public int update(BookVO bookVO) {
        bookDao.save(bookVO);
        return 0;
    }

    @Override
    public int delete(Long id) {
        bookDao.deleteById(id);
        return 0;
    }

	@Override
	public Page<BookVO> pageSelect(Pageable pageable) {
		// pagination의 페이지번호를 클릭햇을때 데이터를 가져오기 쉽도록 index값을 변화
		// getPageNumber값을 0부터 시작하도록
		int page = pageable.getPageNumber() == 0 ? 0: pageable.getPageNumber() -1;
		// 몇페이지의 데이터를 몇개 가져올것인지 설정
		pageable = PageRequest.of(page, 10);
		log.debug(pageable.toString());
		
		return bookDao.findAll(pageable);
	}
}
