package com.lion.convert;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.lion.entity.AbstractEntity;

@FacesConverter(value = "entityConverter", forClass = AbstractEntity.class)
public class EntityConverter implements Converter {

	public Object getAsObject(FacesContext ctx, UIComponent component,
			String value) {
		if (value != null) {
			return this.getAttributesFrom(component).get(value);
		}
		return null;
	}

	public String getAsString(FacesContext ctx, UIComponent component,
			Object value) {

		if (value != null && !"".equals(value)) {
			AbstractEntity entity = (AbstractEntity) value;

			if (entity != null) {
				this.addAttribute(component, entity);

				if (entity.getAbstractId() != null) {
					return String.valueOf(entity.getAbstractId());
				}
				try {
					return (String) value;
				} catch (Exception e) {
					e.printStackTrace();
					return "";
				}
			}
		}
		return "";
	}

	private void addAttribute(UIComponent component, AbstractEntity o) {
		if (o.getAbstractId() != null) {
			this.getAttributesFrom(component).put(o.getAbstractId().toString(),
					o);
		}
	}

	private Map<String, Object> getAttributesFrom(UIComponent component) {
		return component.getAttributes();
	}

}
