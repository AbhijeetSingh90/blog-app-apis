package blogappapis.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotBlank
	@Size( min=4 , message="Minimum Size of category title is 4")
	private String categoryTitle;
	
	
	@NotBlank
	@Size(min=10, message="Minimum Size of category description is 10")
	private String categoryDescription;


	@Override
	public String toString() {
		return "CategoryDto [categoryId=" + categoryId + ", categoryTitle=" + categoryTitle + ", categoryDescription="
				+ categoryDescription + "]";
	}

}
