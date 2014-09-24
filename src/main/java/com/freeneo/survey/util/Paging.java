package com.freeneo.survey.util;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.springframework.beans.support.PagedListHolder;

public class Paging extends TagSupport {

	private PagedListHolder pageListHolder;
	private int offset;
	private String contextRoot;

	public Paging() {
	}

	public Paging(PagedListHolder pageListHolder) {
		this.pageListHolder = pageListHolder;
	}

	public int doStartTag() throws JspException {
		JspWriter out = null;
		out = pageContext.getOut();
		if (pageListHolder.getNrOfElements() <= pageListHolder.getPageSize()) {
			try {
				out.write("<div class='pagination'><ul><li class='disabled'><a href='#' onclick='return false'>1</a></li></ul></div>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				out.write("<div class='pagination  text-center'><ul>");
				if (pageListHolder.getPageCount() <= offset) {
					for (int i = 0; i < pageListHolder.getPageCount(); i++) {
						if (pageListHolder.getPage() == i) {
							out.write("<li class='disabled'><a href='#' onclick='return false'>" + (pageListHolder.getPage() + 1) + "</a></li>");
						} else {
							out.write("<li><a href=\"" + contextRoot + "/"
									+ (i + 1) + "\">" + (i + 1) + "</a></li>");
						}
					}
				} else {
					if (pageListHolder.isFirstPage()) {
						out.write("<li class='disabled'><a href='#' onclick='return false'>1</a></li>");
						for (int i = 2; i <= offset + 1; i++) {
							out.write("<li><a href=\"/" + contextRoot + "/" + i
									+ "\">" + i + "</a></li>");
						}
						out.write("<li><a href=\"/" + contextRoot
								+ "/next\">&gt;&gt;</a></li>");
						out.write("<li><a href=\"/" + contextRoot
								+ "/last\">&gt;|</a></li>");
					} else if (pageListHolder.isLastPage()) {
						out.write("<li><a href=\"/" + contextRoot
								+ "/first\">|&lt;</a></li>");
						out.write("<li><a href=\"/" + contextRoot
								+ "/prev\">&lt;&lt;</a></li>");
						for (int i = pageListHolder.getPageCount() - offset; i <= pageListHolder
								.getPageCount(); i++) {
							int currentPage = pageListHolder.getPage() + 1;
							if (currentPage == i) {
								out.write("<li class='disabled'><a href='#' onclick='return false'>" + (currentPage) + "</a></li>");
							} else {
								out.write("<li><a href=\"/" + contextRoot + "/" + i
										+ "\">" + i + "</a></li>");
							}
						}
					} else {
						out.write("<li><a href=\"/" + contextRoot
								+ "/first\">|&lt;</a></li>");
						out.write("<li><a href=\"/" + contextRoot
								+ "/prev\">&lt;&lt;</a></li>");
						int currentPage = pageListHolder.getPage() + 1;
						int startingPoint = getStartingPoint(currentPage);
						int endingPoint = getEndingPoint(currentPage);
						int count = 0;
						for (int i = startingPoint; i <= endingPoint; i++) {
							count++;
						}
						if (count == 4) {
							startingPoint -= 1;
						}
						for (int i = startingPoint; i <= endingPoint; i++) {
							if (currentPage == i) {
								out.write("<li class='disabled'><a href='#' onclick='return false'>" + (currentPage) + "</a></li>");
							} else {
								out.write("<li><a href=\"/" + contextRoot + "/" + i
										+ "\">" + i + "</a></li>");
							}
						}
						out.write("<li><a href=\"/" + contextRoot
								+ "/next\">&gt;&gt;</a></li>");
						out.write("<li><a href=\"/" + contextRoot
								+ "/last\">&gt;|</a></li>");
					}
				}
				out.write("</ul></div>");
			} catch (IOException ioe) {
			}

		}
		return 0;
	}

	private int getStartingPoint(int currentPage) {
		if ((currentPage - 2) == 0) {
			return 1;
		} else if ((currentPage - 2) > 0) {
			return currentPage - 2;
		}
		return currentPage - 2;
	}

	private int getEndingPoint(int currentPage) {

		if ((getStartingPoint(currentPage) + offset) > pageListHolder
				.getPageCount()) {
			return pageListHolder.getPageCount();
		} else {
			return getStartingPoint(currentPage) + offset;
		}

	}

	/**
	 * @return the pageListHolder
	 */
	public PagedListHolder getPageListHolder() {
		return pageListHolder;
	}

	/**
	 * @param pageListHolder
	 *            the pageListHolder to set
	 */
	public void setPageListHolder(PagedListHolder pageListHolder) {
		this.pageListHolder = pageListHolder;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getContextRoot() {
		return contextRoot;
	}

	public void setContextRoot(String contextRoot) {
		this.contextRoot = contextRoot;
	}
}