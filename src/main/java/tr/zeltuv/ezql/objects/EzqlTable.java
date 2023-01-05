package tr.zeltuv.ezql.objects;

import tr.zeltuv.ezql.exception.EzqlColumnOrderException;

import java.util.*;
import java.util.stream.Collectors;

public class EzqlTable {

    private String name;
    private EzqlDatabase database;

    private List<EzqlColumn> columns = new ArrayList<>();

    protected EzqlTable(String name, EzqlDatabase database, EzqlColumn... ezqlColumn) {
        this.name = name;
        this.database = database;
        columns.addAll(Arrays.asList(ezqlColumn));
    }

    public List<EzqlColumn> getColumns() {
        return columns;
    }

    public EzqlColumn getColumn(String name) {
        return columns.stream().filter(column -> column.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    public String getName() {
        return name;
    }

    public void pushRow(Object... values) {
        if (values.length != columns.size())
            throw new EzqlColumnOrderException(values.length, columns.size());

        database.getEzqlQuery().addRow(this, values);
    }

    public LinkedList<EzqlRow> getAllRows(Set<String> neededColumns){
        return database.getEzqlQuery().getAllRows(this,neededColumns);
    }

    public LinkedList<EzqlRow> getAllRows(){
        return database.getEzqlQuery().getAllRows(this,getColumnsName());
    }

    public Set<String> getColumnsName(){
        return columns.stream().map(EzqlColumn::getName).collect(Collectors.toSet());
    }

    public List<EzqlRow> getRows(String where, String whereValue) {
        return getRows(where,whereValue,getColumnsName());
    }
    public EzqlRow getSingleRow(String where, String whereValue) {
        return getSingleRow(where,whereValue,getColumnsName());
    }
    public List<EzqlRow> getRows(String where, String whereValue, Set<String> neededColumns) {
        return database.getEzqlQuery().getRows(this,where,whereValue, neededColumns);
    }

    public EzqlRow getSingleRow(String where, String whereValue, Set<String> neededColumns) {
        return database.getEzqlQuery().getSingleRow(this,where,whereValue,neededColumns);
    }

    public Object getSingleValue(String requestedValue, String where, String whereValue) {
        EzqlRow ezqlRow = getSingleRow(where,whereValue,Set.of(requestedValue));

        return ezqlRow.getValue(requestedValue);
    }

    public void removeRows(String where,String whereValue){
        database.getEzqlQuery().remove(this,where,whereValue);
    }

    public void updateRow(String where, String whereValue, EzqlRow ezqlRow){
        database.getEzqlQuery().update(this,where,whereValue,ezqlRow);
    }

    protected EzqlDatabase getDatabase() {
        return database;
    }
}
