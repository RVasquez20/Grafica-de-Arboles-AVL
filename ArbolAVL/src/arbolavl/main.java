/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbolavl;

import Vista.Main;
import static Vista.Main.Image;
import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author Desarrollo
 */
public class main {
final static String Direccion="C:\\Users\\rodri\\Documents\\GitHub\\Grafica-de-Arboles-AVL";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        ArbolAVL arbolAVL = new ArbolAVL();
        Main V=new Main();
        String mostrar="";
        int opc,numero;
        do{
            opc=0;numero=0;
            try{
           numero=Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese El numero a añadir al arbol:","Ingreso",JOptionPane.QUESTION_MESSAGE));
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
           
                arbolAVL.insertar(numero);
         
               opc=JOptionPane.showConfirmDialog(null, "Desea Ingresar Otro Numero?","Seguir?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
           
            
            if(opc!=JOptionPane.NO_OPTION){
                mostrar+=String.valueOf(numero)+",";
            }else{
                mostrar+=String.valueOf(numero)+".";
            }
        }while(opc!=JOptionPane.NO_OPTION);
        
         String NombreImagen="\\ArbolAVL\\arbol_numeros.jpg";        
        
        arbolAVL.graficar(Direccion+NombreImagen);
        Thread.sleep(3000);
        Eliminar();
        V.RepList.setText(mostrar);
        
        Image.setIcon(new javax.swing.ImageIcon(Direccion+NombreImagen));
        V.show();
       
    }
    
    public static void Eliminar(){
          try{
               String NombreDot=Direccion+"\\ArbolAVL\\aux_grafico.dot";
            File archivo = new File(NombreDot);

            boolean estatus = archivo.delete();;

            if (!estatus) {

                System.out.println("Error no se ha podido eliminar el  archivo");

           }

        }catch(Exception e){

           System.out.println(e.getMessage());

        }
    }
    
}
