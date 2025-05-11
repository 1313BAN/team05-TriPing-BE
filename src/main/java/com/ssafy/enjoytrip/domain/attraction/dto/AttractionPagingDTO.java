package com.ssafy.enjoytrip.domain.attraction.dto;

import com.ssafy.enjoytrip.domain.attraction.model.Attraction;

import java.util.List;

public class AttractionPagingDTO {
	private List<Attraction> attractions;
	private int totalPage;
	private int currentPage;
	
	public AttractionPagingDTO(List<Attraction> attractions, int totalPage, int currentPage) {
		this.attractions = attractions;
		this.totalPage = totalPage;
		this.currentPage = currentPage;
	}
	
	public List<Attraction> getAttractions() {
		return attractions;
	}
	public void setAttractions(List<Attraction> attractions) {
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