/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao.queryobject;

/**
 *
 * @author Daniel
 */
public class ViewProperties {
    private final String viewName;
    private final String groupBy;
    private final String whereClause;

    public ViewProperties(String view, String groupBy, String where) {
        this.viewName = view;
        this.groupBy = groupBy;
        this.whereClause = where;
    }

    public String getViewName() {
        return viewName;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public String getWhereClause() {
        return whereClause;
    }

}
