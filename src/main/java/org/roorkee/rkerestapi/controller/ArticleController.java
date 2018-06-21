package org.roorkee.rkerestapi.controller;

import org.roorkee.rkerestapi.dao.AbstractDao;
import org.roorkee.rkerestapi.dao.ArticleDao;
import org.roorkee.rkerestapi.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin
public class ArticleController extends AbstractBaseController<Article>{

    @Autowired
    private ArticleDao dao;

//    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
//    public long post(@RequestBody Article content) {
//        return dao.save(content);
//    }

    @GetMapping(path="/", produces = "application/json")
    public List<Article> list(){
        return dao.list();
    }

    @PutMapping(path= "/", consumes = "application/json", produces = "application/json")
    public long[] put(List<Article> articles){
        return null;
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public Article get(@PathVariable Long id) {
        return dao.get(id);
    }

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
	}
	
	@Override
	Long save(Article entity) {
		return super.save(entity);
	}

	@Override
	AbstractDao<Article> getDao() {
		return dao;
	}

}
