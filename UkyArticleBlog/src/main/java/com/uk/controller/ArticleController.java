package com.uk.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ArticleController {

	private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);
	@Autowired
	private ArticleService articleService;

	/**
	 * This is the method called for Login
	 * 
	 * @param error=
	 *            If there is any error in login
	 * @param logout
	 *            = To show that user has logged out
	 * @return model
	 */
	@GetMapping("/login")
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		log.info("Login");

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;

	}

	/**
	 * This method is for displaying 403 access denied page when users with role
	 * other than admin/user.
	 * 
	 * @return Screen showing access denied
	 */
	//
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();

			model.addObject("username", userDetail.getUsername());

		}

		model.setViewName("403");
		return model;

	}

	/**
	 * This method is to call the welcome screen
	 * 
	 * @param request
	 *            Httpservlet Request
	 * @return welcome Screen
	 */

	@GetMapping("/")
	public String home(HttpServletRequest request) {
		request.setAttribute("mode", "MODE_HOME");
		return "welcome";
	}

	/**
	 * This method is used to display screen to create new article
	 * 
	 * @param request
	 *            - Http Servlet Request parameters
	 * @return - The screen to enter article title and description
	 */
	@GetMapping("/new-article")
	public String create(HttpServletRequest request) {

		request.setAttribute("mode", "MODE_NEW");
		return "welcome";
	}

	/**
	 * This method is used to save the acrticle
	 * 
	 * @param article
	 *            - contains the details related to the article
	 * @param bindingResult
	 *            - Binding Result
	 * @param request
	 *            - HttpServlet Request
	 * @param session
	 *            - Contains session information
	 * @return - After saving the article it displays all the articles.
	 */
	@PostMapping("/save-article")
	public String saveArticle(@ModelAttribute ArticleDTO article, BindingResult bindingResult,
			HttpServletRequest request, HttpSession session) {
		String userName = request.getRemoteUser();
		articleService.create(userName, article);
		int page = 0;
		session.setAttribute("page", page);
		request.setAttribute("articles",
				articleService.findAll(new PageRequest(page, 3, Direction.ASC, "createdDate")));

		request.setAttribute("mode", "MODE_ARTICLES");
		request.setAttribute("All", "MODE_ALL");
		log.info("Successfully saved article");
		return "welcome";
	}

	/**
	 * This Method is called when the admin tries to verify/approve an article
	 * 
	 * @param id
	 *            - This is the id of the article which need to be verified
	 * @param request-
	 *            This has the Httpservlet request attributes
	 * @return - returns to the welcome screen after approval of the article
	 */

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/verify-article")
	public String verifyArticle(@RequestParam int id, HttpServletRequest request) {

		request.setAttribute("article", articleService.updateVerify(id));
		request.setAttribute("mode", "MODE_HOME");
		log.info("Successfully Updated Reply Flag {}: " + id);
		return "welcome";
	}

	/**
	 * This Method is called when the admin tries to reject an article
	 * 
	 * @param id
	 *            - This is the id of the article which need to be verified
	 * @param request
	 *            - This has the Httpservlet request attributes
	 * @return - returns to the welcome screen after approval of the article
	 */

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/reject-article")
	public String rejectArticle(@RequestParam int id, HttpServletRequest request) {

		request.setAttribute("article", articleService.updateDelFlg(id));
		log.info("Successfully rejected article Id:" + id);
		request.setAttribute("mode", "MODE_HOME");
		return "welcome";
	}
	/**
	 * This Method is called when the admin tries to verify/approve an article comment
	 * 
	 * @param id
	 *            - This is the id of the reply which need to be verified
	 * @param request-
	 *            This has the Httpservlet request attributes
	 * @return - returns to the welcome screen after approval of the comment
	 */

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/verify-reply")
	public String verifyReply(@RequestParam int id, HttpServletRequest request) {

		request.setAttribute("article", articleService.updateVerifyReply(id));
		request.setAttribute("mode", "MODE_HOME");
		log.info("Successfully Updated Reply Flag {}: " + id);
		return "welcome";
	}

	/**
	 * This Method is called when the admin tries to reject an article comment
	 * 
	 * @param id
	 *            - This is the id of the article for which comments need to be rejected
	 * @param request
	 *            - This has the Httpservlet request attributes
	 * @return - returns to the welcome screen after rejecting article
	 */

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/reject-reply")
	public String rejectReply(@RequestParam int replyId, HttpServletRequest request) {

		request.setAttribute("article", articleService.updateDelFlgReply(replyId));
		log.info("Successfully rejected reply Id:" + replyId);
		request.setAttribute("mode", "MODE_HOME");
		return "welcome";
	}
	/**
	 * This method is called when All Articles tab is clicked to view all the
	 * articles.
	 * 
	 * @param page
	 *            - page Number
	 * @param size
	 *            - The number of articles that need to be shown on a single
	 *            page
	 * @param request
	 *            - HttpServlet request contains all the request parameters.
	 * @param session-
	 *            HttpSession contains all the session information
	 * @return- To welcome screen to display all the articles
	 */

	//@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/all-articles")
	public String allArticles(@RequestParam int page, @RequestParam int size, HttpServletRequest request,
			HttpSession session) {
		session.setAttribute("page", page);
		request.setAttribute("articleSize", articleService.findAll(new PageRequest(page, size, Direction.ASC, "createdDate")).size());
		request.setAttribute("articles",
				articleService.findAll(new PageRequest(page, size, Direction.ASC, "createdDate")));
		request.setAttribute("mode", "MODE_ARTICLES");
		request.setAttribute("All", "MODE_ALL");
		log.info("Displayed all articles" + size);
		return "welcome";
	}

	/**
	 * The getNext function is called when the Next button is clicked to view
	 * more articles.
	 * 
	 * @param request
	 *            - HttpServlet request contains all the request parameters.
	 * @param session
	 *            HttpSession contains all the session information
	 * @return returns to the welcome screen to display the page next page
	 *         results
	 */
	@GetMapping("/getNext")
	public String getNext(HttpServletRequest request, HttpSession session) {

		Integer page = (Integer) session.getAttribute("page");
		page++;
		session.setAttribute("page", page);

		List<ArticleDTO> articles = articleService.findAll(new PageRequest(page, 3, Direction.ASC, "createdDate"));
		if (articles.size() == 0) {
			page = 0;
			session.setAttribute("page", page);
			request.setAttribute("articles",
					articleService.findAll(new PageRequest(page, 3, Direction.ASC, "createdDate")));
		}
		request.setAttribute("articles",
				articleService.findAll(new PageRequest(page, 3, Direction.ASC, "createdDate")));
		request.setAttribute("mode", "MODE_ARTICLES");
		request.setAttribute("All", "MODE_ALL");
		return "welcome";
	}

	/**
	 * The getPrevious function is called when the Next button is clicked to
	 * view more articles.
	 * 
	 * @param request
	 *            - HttpServlet request contains all the request parameters.
	 * @param session
	 *            HttpSession contains all the session information
	 * @return returns to the welcome screen to display the previous page
	 *         results
	 */

	@GetMapping("/getPrevious")
	public String getPrevious(HttpServletRequest request, HttpSession session) {
		Integer page = (Integer) session.getAttribute("page");

		page--;
		if (page < 0) {
			page = 0;
		}
		session.setAttribute("page", page);
		List<ArticleDTO> articles = articleService.findAll(new PageRequest(page, 3, Direction.ASC, "createdDate"));
		if (articles.size() == 0) {
			page = 1;
			session.setAttribute("page", page);
			request.setAttribute("articles",
					articleService.findAll(new PageRequest(page, 3, Direction.ASC, "createdDate")));
		}
		request.setAttribute("articles",
				articleService.findAll(new PageRequest(page, 3, Direction.ASC, "createdDate")));

		request.setAttribute("mode", "MODE_ARTICLES");
		request.setAttribute("All", "MODE_ALL");
		return "welcome";
	}

	/**
	 * This Method is used to view all the verified articles
	 * 
	 * @param request
	 *            - Http Servlet Request parameters
	 * @return - return screen displaying all verified articles
	 */
	@GetMapping("/all-articles-verify")
	public String allArticlesUser(HttpServletRequest request) {
		request.setAttribute("articles", articleService.findAllByVerify());
		request.setAttribute("mode", "MODE_ARTICLES");
		return "welcome";
	}

	/**
	 * This Method is used to show all the articles created by the logged in
	 * user
	 * 
	 * @param request-
	 *            Http Servlet Request parameters
	 * @return screen display all the articles created by the logged in iser
	 */
	
	@GetMapping("/my-articles")
	public String userArticles(HttpServletRequest request) {
		request.setAttribute("articles", articleService.findAllByCreatedBy(request.getRemoteUser()));
		request.setAttribute("mode", "MODE_ARTICLES");
		return "welcome";
	}

	/**
	 * This method is used to update the article with a given article Id
	 * 
	 * @param id
	 *            - Article Id
	 * @param request
	 *            - Http Servlet Request Parameters
	 * @return- return the page to update the article Id.
	 */
	@GetMapping("/update-article")
	public String updateTask(@RequestParam int id, HttpServletRequest request) {

		request.setAttribute("article", articleService.findById(id));

		request.setAttribute("mode", "MODE_UPDATE");
		log.info("Successfully updated task with article id " + id);
		return "welcome";
	}

	/**
	 * This method is used to delete an article created by the user
	 * 
	 * @param id
	 *            - Article Id which needs to be deleted
	 * @param request
	 *            - Http Servlet Request parameters.
	 * @param session
	 *            - Contains session information
	 * @return - return to screen showing all articles
	 */
	@GetMapping("/delete-article")
	public String deleteArticle(@RequestParam int id, HttpServletRequest request, HttpSession session) {

		int page = 0;
		articleService.removeArticle(id);
		session.setAttribute("page", page);
		request.setAttribute("articles",
				articleService.findAll(new PageRequest(page, 3, Direction.ASC, "createdDate")));
		request.setAttribute("articleSize", articleService.findAll(new PageRequest(page, 3, Direction.ASC, "createdDate")).size());
		request.setAttribute("mode", "MODE_ARTICLES");
		request.setAttribute("All", "MODE_ALL");
		log.info("Deleted Article Successfully{}" + id);
		return "welcome";
	}

	/**
	 * This method is used to search by Article Title
	 * 
	 * @param title
	 *            Article Title to be used for search
	 * @param request-
	 *            Http Servlet request parameters
	 * @return - The screen showing all the articles matching the search
	 *         criteria
	 */
	@GetMapping("/find-article")
	public String findArticle(@RequestParam String title, HttpServletRequest request) {
		request.setAttribute("articles", articleService.findByTitle(title));

		request.setAttribute("mode", "MODE_ARTICLES");
		request.setAttribute("articleSize", articleService.findByTitle(title).size());
		log.info("Found Article" + articleService.findByTitle(title).size());
		return "welcome";
	}

	/**
	 * This method shows the comments on a particular article
	 * 
	 * @param id
	 *            - Article Id
	 * @param request
	 *            -HttpServlet request parameters
	 * @return screen to display comments for a particular article Id
	 */
	@GetMapping("/new-reply")
	public String reply(@RequestParam int id, HttpServletRequest request) {

		request.setAttribute("article", articleService.findById(id));
		request.setAttribute("mode", "MODE_REPLY");
		log.info("Creating new Reply");
		return "welcome";
	}

	/**
	 * This method lets us post or view existing comments on a particular
	 * article
	 * 
	 * @param id
	 *            - Article id for which comments need to be retrieved,
	 * @param request
	 *            HttpServlet request parameters
	 * @return The Screen showing the article and the comments for that article
	 */
	@GetMapping("/read-more")
	public String comments(@RequestParam int id, HttpServletRequest request) {

		request.setAttribute("article", articleService.findById(id));
		request.setAttribute("replies",articleService.getComments(id) );
		/*articleService.findById(id).getReply()*/
		log.info("Displayed comment section");
		return "article";
	}

	

	/**
	 * This method is used to save reply information in the Reply table
	 * 
	 * @param articleDTO
	 *            - contains the details related to the article
	 * @param bindingResultArticle
	 *            Binding Result
	 * @param replyDTO
	 *            - contains the details related to the reply
	 * @param bindingResult
	 *            -Binding Result Reply
	 * @param request
	 *            - HttpServlet request parameters
	 * @return Comments section
	 */
	@PostMapping("/save-reply")
	public String saveReply(@ModelAttribute ArticleDTO articleDTO, BindingResult bindingResultArticle,
			@ModelAttribute ReplyDTO replyDTO, BindingResult bindingResult, HttpServletRequest request,HttpSession session) {
		String userName = request.getRemoteUser();
		articleService.createReply(articleDTO.getId(), userName, replyDTO);

		request.setAttribute("article", articleService.findById(articleDTO.getId()));
		request.setAttribute("replies", articleService.findById(articleDTO.getId()).getReply());
		
		/*request.setAttribute("mode", "MODE_READ");
		log.info("Successfully saved getReplyReply{}: " + articleService.findById(articleDTO.getId()).getReply());
		log.info("Successfully saved Reply{}: " + replyDTO);
		return "article";*/
		int page = 0;
		session.setAttribute("page", page);
		request.setAttribute("articles",
				articleService.findAll(new PageRequest(page, 3, Direction.ASC, "createdDate")));

		request.setAttribute("mode", "MODE_ARTICLES");
		request.setAttribute("All", "MODE_ALL");
		log.info("Successfully saved article");
		return "welcome";
	}

}
