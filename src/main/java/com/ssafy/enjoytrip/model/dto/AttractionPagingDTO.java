package com.ssafy.enjoytrip.model.dto;

import java.util.List;

public class AttractionPagingDTO {
	private List<AttractionDTO> attractions;
	private int totalPage;
	private int currentPage;
	
	public AttractionPagingDTO(List<AttractionDTO> attractions, int totalPage, int currentPage) {
		this.attractions = attractions;
		this.totalPage = totalPage;
		this.currentPage = currentPage;
	}
	
	public List<AttractionDTO> getAttractions() {
		return attractions;
	}
	public void setAttractions(List<AttractionDTO> attractions) {
		this.attractions = attractions;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	@Override
	public String toString() {
		return "AttractionPagingDTO [attractions=" + attractions + ", totalPage=" + totalPage + ", currentPage="
				+ currentPage + "]";
	}
}