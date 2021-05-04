/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.projetoagendajpa;

import entidade.Contato;
import entidade.Telefone;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import sun.reflect.Reflection;

/**
 *
 * @author andre
 */
public class TesteReflexao {
    
    public static void pegaCampos(Class classe) throws InstantiationException, IllegalAccessException{
        
               
        Object o= classe.newInstance();
        
        
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }
        
        System.out.println(fields);
    }
    public static void salvar(Object o) throws InstantiationException, IllegalAccessException, NoSuchFieldException{
        List<Class> lista = new LinkedList();
        lista.add(Contato.class);
        
        String insert="insert into "+ o.getClass().getSimpleName()+" ";
        String campos="(";
        String valores="(";    
        Object aux;
        Field[] declaredFields = o.getClass().getDeclaredFields();
        for (Field item : declaredFields) {
            if(!item.getType().equals(List.class)){
                campos+=item.getName()+",";
                item.setAccessible(true);
                
                if((item.getType().equals(String.class)||item.getType().equals(Date.class)) 
                        &&item.get(o)!=null)
                    valores+="'"+item.get(o)+"',";
                if(lista.contains(item.getType())&&item.get(o)!=null){
                   aux =item.get(o);
                   
                    Field field = aux.getClass().getDeclaredField("id");
                    field.setAccessible(true);
                    valores+=field.get(item.get(o))+",";;
                } 
                else 
                    valores+=item.get(o)+",";;
            }
        }
        campos= campos.substring(0, campos.length()-1)+") values ";
        valores= valores.substring(0, valores.length()-1)+")";
        System.out.println(insert+campos+valores);
    }
    
    public static void main(String[] args) {
        
        try{
            Contato contato= new Contato();
            contato.setId(1222);
            contato.setNome("Andre");
            
           // salvar(contato);
            
            Telefone t = new Telefone();
            t.setContato(contato);
            salvar(t);
            
        }catch(Exception e){
            System.err.print("erro"+e);
        }
    }
    
    
}

