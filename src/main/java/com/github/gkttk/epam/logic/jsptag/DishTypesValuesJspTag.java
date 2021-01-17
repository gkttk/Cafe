package com.github.gkttk.epam.logic.jsptag;

import com.github.gkttk.epam.model.enums.DishType;

import javax.servlet.jsp.tagext.TagSupport;

public class DishTypesValuesJspTag extends TagSupport {


    @Override
    public int doStartTag() {
        DishType[] types = DishType.values();
        pageContext.getRequest().setAttribute("dishTypes", types);

        return SKIP_BODY;
    }


}
