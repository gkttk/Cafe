package com.github.gkttk.epam.logic.jsptag;

import com.github.gkttk.epam.model.enums.DishTypes;

import javax.servlet.jsp.tagext.TagSupport;

public class DishTypesValuesJspTag extends TagSupport {


    @Override
    public int doStartTag() {
        DishTypes[] types = DishTypes.values();
        pageContext.getRequest().setAttribute("dishTypes", types);

        return SKIP_BODY;
    }


}
