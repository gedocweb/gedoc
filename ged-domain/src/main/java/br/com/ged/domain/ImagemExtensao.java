package br.com.ged.domain;

public enum ImagemExtensao {

	JPG("jpg"),
	JPEG("jpeg"),
	PNG("png"),
	GIF("gif"),
	BMP("bmp");
	
	private String label;
	
	private ImagemExtensao(String label){
		this.label = label;
	}
	
	public static boolean isImagem(String extensao){
		
		for (ImagemExtensao ex : values()){
			
			if (ex.label.equalsIgnoreCase(extensao)){
				return true;
			}
		}
		
		return false;
	}

	public String getLabel() {
		return label;
	}
}
