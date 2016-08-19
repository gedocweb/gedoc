package br.com.ged.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

import br.com.ged.util.InitMessageProperties;

public enum Role {

	ADMIN(InitMessageProperties.getValue(Mensagem.MSA001)),
	GERENTE(InitMessageProperties.getValue(Mensagem.MSA004)),
	COLABORADOR(InitMessageProperties.getValue(Mensagem.MSA005));
	
	private String label; 
	
	private Role(String label){
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static List<String> getLabelRoles(){
		
		List<String> listLabel = new ArrayList<String>();
		
		for (Role role : values()){
			
			listLabel.add(role.getLabel());
		}
		
		return listLabel;
	}
	
	public static Set<Role> getRolesPorLabel(String[] roles){
		
		Set<Role> listRoles = new HashSet<Role>();
		
		for (Role role : values()){
			
			for (String roleLabel : roles){
				
				if (role.getLabel().equals(roleLabel)){
					
					listRoles.add(role);
				}
			}
		}
		
		return listRoles;
	}

	public static List<SelectItem> selectItems() {

		List<SelectItem> list = new ArrayList<>();
		
		for (Role role : values()){
			
			SelectItem si = new SelectItem(role, role.label);
			
			list.add(si);
		}
		
		return list;
	}
	
	public static List<SelectItem> selectItemsGerencia() {

		List<SelectItem> list = new ArrayList<>();
		
		for (Role role : values()){
			
			if (role.equals(ADMIN)){
				continue;
			}
			
			SelectItem si = new SelectItem(role, role.label);
			
			list.add(si);
		}
		
		return list;
	}
}