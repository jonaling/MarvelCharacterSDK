package MarvelSDK.character.Model;

import lombok.Getter;

@Getter
public enum OrderByEnum {
	nameAsc("name"), modifiedAsc("modified"), nameDsc("-name"), modifiedDsc("-modified");

	private final String orderByEnum;

	OrderByEnum(String orderByEnum) {
		this.orderByEnum = orderByEnum;
	}
	
	 public String getOrderByValue() {
	        return orderByEnum;
	    }
}
