package MarvelSDK.character.Model;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import MarvelSDK.character.MD5Converter;

@Data
public class CharactersRequest {
	private String apiKey;
	private String privateKey;
	private String hash;
	private String tn;
	private String name;
	private String nameStartsWith;
	private LocalDate modifiedSince;
	private List<String> comicsIds;
	private List<String> seriesIds;
	private List<String> eventIds;
	private List<String> storyIds;
	private OrderByEnum orderBy;
	private int limit;
	private int offset;
	private Boolean useCache;

	public String getApiKey() {
		return apiKey;
	}

	public String getHash() {
		return hash;
	}

	public String getName() {
		return name;
	}

	public String getNameStartsWith() {
		return nameStartsWith;
	}

	public String getModifiedSince() {
		if (modifiedSince != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");
			return modifiedSince.format(formatter);
		}
		return null;
	}

	public String getComicsIds() {
		if (this.comicsIds != null) {
			String comicsIds = this.comicsIds.stream().collect(Collectors.joining(", "));
			return comicsIds;
		}
		return null;
	}

	public String getSeriesIds() {
		if (this.seriesIds != null) {
			String seriesIds = this.seriesIds.stream().collect(Collectors.joining(", "));
			return seriesIds;
		}
		return null;
	}

	public String getEventIds() {
		if (this.eventIds != null) {
			String eventIds = this.eventIds.stream().collect(Collectors.joining(", "));
			return eventIds;
		}
		return null;
	}

	public String getStoryIds() {
		if (this.storyIds != null) {
			String storyIds = this.storyIds.stream().collect(Collectors.joining(", "));
			return storyIds;
		}
		return null;
	}

	public String getOrderBy() {
		if (orderBy != null) {
			return orderBy.getOrderByValue();
		}
		return null;
	}

	public String getLimit() {
		if (limit != 0) {
			return "" + limit;
		}
		return null;
	}

	public String getOffset() {
		if (offset != 0) {
			return "" + offset;
		}
		return null;
	}

	public String getTn() {
		return tn;
	}

	public Boolean getUseCache() {
		return useCache;
	}

	// Static Builder class
	public static class Builder {
		private String apiKey;
		private String privateKey;
		private String tn;
		private String hash;
		private String name;
		private String nameStartsWith;
		private LocalDate modifiedSince;
		private List<String> comicsIds;
		private List<String> seriesIds;
		private List<String> eventIds;
		private List<String> storyIds;
		private OrderByEnum orderBy;
		private int limit;
		private int offset;
		private Boolean useCache;

		// Builder constructor
		public Builder(String apiKey, String privateKey) {
			this.apiKey = apiKey;
			this.privateKey = privateKey;
			this.tn = String.valueOf(System.currentTimeMillis()); // Generated timestamp
			this.hash = generateHash();
			this.useCache = true;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder nameStartsWith(String nameStartsWith) {
			this.nameStartsWith = nameStartsWith;
			return this;
		}

		public Builder modifiedSince(LocalDate modifiedSince) {
			this.modifiedSince = modifiedSince;
			return this;
		}

		public Builder comicsIds(List<String> comicsIds) {
			this.comicsIds = comicsIds;
			return this;
		}

		public Builder seriesIds(List<String> seriesIds) {
			this.seriesIds = seriesIds;
			return this;
		}

		public Builder eventIds(List<String> eventIds) {
			this.eventIds = eventIds;
			return this;
		}

		public Builder storyIds(List<String> storyIds) {
			this.storyIds = storyIds;
			return this;
		}

		public Builder orderBy(OrderByEnum orderBy) {
			this.orderBy = orderBy;
			return this;
		}

		public Builder limit(int limit) {
			this.limit = limit;
			return this;
		}

		public Builder offset(int offset) {
			this.offset = offset;
			return this;
		}

		public Builder noCache() {
			this.useCache = false;
			return this;
		}

		public CharactersRequest build() {
			CharactersRequest request = new CharactersRequest();
			request.apiKey = this.apiKey;
			request.privateKey = this.privateKey;
			request.hash = this.hash;
			request.tn = this.tn;
			request.name = this.name;
			request.nameStartsWith = this.nameStartsWith;
			request.modifiedSince = this.modifiedSince;
			request.comicsIds = this.comicsIds;
			request.seriesIds = this.seriesIds;
			request.eventIds = this.eventIds;
			request.storyIds = this.storyIds;
			request.orderBy = this.orderBy;
			request.limit = this.limit;
			request.offset = this.offset;
			request.useCache = this.useCache;
			return request;
		}

		private String generateHash() {
			// Generate the hash based on the current state
			return MD5Converter.generateHash(this.tn + this.privateKey + this.apiKey);
		}
	}

}