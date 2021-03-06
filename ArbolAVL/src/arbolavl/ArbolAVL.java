

package arbolavl;

import java.io.FileWriter;
import java.io.PrintWriter;

public class ArbolAVL {
    NodoArbolAVL raiz;
    
    public ArbolAVL(){
        raiz = null;
    }
    
    //BUSCAR
    public NodoArbolAVL buscar(int d, NodoArbolAVL r){
        if(raiz == null){
            return null;
        }else if(r.dato == d){
            return r;
        }else if(r.dato < d){
            return buscar(d, r.hijoDerecho);
        }else{
            return buscar(d, r.hijoIzquierdo);
        }
    }
    
    public int obtenerFE(NodoArbolAVL x){
        if(x == null)
            return -1;
        else
            return x.fe;
    }
    
    public NodoArbolAVL rotacionIzquierda(NodoArbolAVL c){
        NodoArbolAVL auxiliar = c.hijoIzquierdo;
        c.hijoIzquierdo = auxiliar.hijoDerecho;
        auxiliar.hijoDerecho = c;
        c.fe = Math.max(obtenerFE(c.hijoIzquierdo) + 1, obtenerFE(c.hijoDerecho) + 1);
        auxiliar.fe = Math.max(obtenerFE(auxiliar.hijoIzquierdo) + 1, obtenerFE(auxiliar.hijoDerecho) + 1);
        return auxiliar;
    }
    
    public NodoArbolAVL rotacionDerecha(NodoArbolAVL c){
        NodoArbolAVL auxiliar = c.hijoDerecho;
        c.hijoDerecho = auxiliar.hijoIzquierdo;
        auxiliar.hijoIzquierdo = c;
        c.fe = Math.max(obtenerFE(c.hijoIzquierdo) + 1, obtenerFE(c.hijoDerecho) + 1);
        auxiliar.fe = Math.max(obtenerFE(auxiliar.hijoIzquierdo) + 1, obtenerFE(auxiliar.hijoDerecho) + 1);
        return auxiliar;
    }
    
    public NodoArbolAVL rotacionDobleIzquierda(NodoArbolAVL c){
        NodoArbolAVL temporal;
        c.hijoIzquierdo = rotacionDerecha(c.hijoIzquierdo);
        temporal = rotacionIzquierda(c);
        return temporal;
    }
    
    public NodoArbolAVL rotacionDobleDerecha(NodoArbolAVL c){
        NodoArbolAVL temporal;
        c.hijoDerecho = rotacionIzquierda(c.hijoDerecho);
        temporal = rotacionDerecha(c);
        return temporal;
    }
    
    public NodoArbolAVL insertarAVL(NodoArbolAVL nuevo, NodoArbolAVL subArb){
        NodoArbolAVL nuevoPadre = subArb;
        
        if(nuevo.dato < subArb.dato){
            if(subArb.hijoIzquierdo == null)
                subArb.hijoIzquierdo = nuevo;
            else
            {
                subArb.hijoIzquierdo = insertarAVL(nuevo, subArb.hijoIzquierdo);
                if((obtenerFE(subArb.hijoIzquierdo) - obtenerFE(subArb.hijoDerecho)== 2))
                    if(nuevo.dato < subArb.hijoIzquierdo.dato)
                        nuevoPadre = rotacionIzquierda(subArb);
                    else
                        nuevoPadre = rotacionDobleIzquierda(subArb);
            }                                        
        }else if(nuevo.dato > subArb.dato){
            if(subArb.hijoDerecho == null)
                subArb.hijoDerecho = nuevo;
            else
            {
                subArb.hijoDerecho = insertarAVL(nuevo, subArb.hijoDerecho);
                if((obtenerFE(subArb.hijoDerecho) - obtenerFE(subArb.hijoIzquierdo) == 2))
                    if(nuevo.dato > subArb.hijoDerecho.dato)
                        nuevoPadre = rotacionDerecha(subArb);
                    else
                        nuevoPadre = rotacionDobleDerecha(subArb);
            }        
        }else
            System.out.println("¡Nodo duplicado!");
    
        //ACTUALIZANDO LAS ALTURAS
        if(subArb.hijoIzquierdo == null && subArb.hijoDerecho != null)
            subArb.fe = subArb.hijoDerecho.fe + 1;
        else if(subArb.hijoDerecho == null && subArb.hijoIzquierdo != null)
            subArb.fe = subArb.hijoIzquierdo.fe + 1;
        else
            subArb.fe = Math.max(obtenerFE(subArb.hijoIzquierdo), obtenerFE(subArb.hijoDerecho))+1;

        return nuevoPadre;
    }
    
    public void insertar (int d){
        NodoArbolAVL nuevo = new NodoArbolAVL(d);
        if(raiz == null)
            raiz = nuevo;
        else
            raiz = insertarAVL(nuevo, raiz);
    }
    
     public void inOrden(NodoArbolAVL n){
        if(n != null)
        {
            inOrden(n.hijoIzquierdo);
            System.out.println(n.dato + ", ");
            inOrden(n.hijoDerecho);
        }
    }
     
     public void preOrden(NodoArbolAVL n){
        if(n != null)
        {
            System.out.println(n.dato + ", ");
            preOrden(n.hijoIzquierdo);
            preOrden(n.hijoDerecho);
        }
    }
     
     public void postOrden(NodoArbolAVL n){
        if(n != null)
        {
            postOrden(n.hijoIzquierdo);
            postOrden(n.hijoDerecho);
            System.out.println(n.dato + ", ");
        }
    }
     
    public void graficar(String path) {
        raiz.graficar(path);
    }
     
    
     private static int correlativo=1;
     
    private class NodoArbolAVL {
        int dato, fe;
        NodoArbolAVL hijoIzquierdo;
        NodoArbolAVL hijoDerecho;
   
   
    private final int id;    
    
        public NodoArbolAVL(int d){
            this.dato = d;
            this.fe = 0;
            this.hijoIzquierdo = null;
            this.hijoDerecho = null;
this.id=correlativo++; 
        }
        
        public void graficar(String path) {
        FileWriter fichero = null;
        PrintWriter escritor;
        try
        {
            fichero = new FileWriter("aux_grafico.dot");
            escritor = new PrintWriter(fichero);
            escritor.print(getCodigoGraphviz());
        } 
        catch (Exception e){
            System.err.println("Error al escribir el archivo aux_grafico.dot");
        }finally{
           try {
                if (null != fichero)
                    fichero.close();
           }catch (Exception e2){
               System.err.println("Error al cerrar el archivo aux_grafico.dot");
           } 
        }                        
        try{
           String dotPath = "C:\\Archivos de programa\\Graphviz\\bin\\dot.exe";
          Runtime rt = Runtime.getRuntime();
          String[] CMD=new String[5];
          CMD[0]=dotPath;
          CMD[1]="-Tjpg";
          CMD[2]="C:\\Users\\rodri\\Desktop\\Curso Vim\\ArbolAVL\\ArbolAVL\\aux_grafico.dot";
          CMD[3]="-o";
          CMD[4]=path;
          rt.exec(CMD);
          //Esperamos medio segundo para dar tiempo a que la imagen se genere.
          //Para que no sucedan errores en caso de que se decidan graficar varios
          //árboles sucesivamente.
          Thread.sleep(500);
        } catch (Exception ex) {
            System.err.println("Error al generar la imagen para el archivo aux_grafico.dot    "+ex.getMessage());
        }            
    }
    /**
     * Método que retorna el código que grapviz usará para generar la imagen 
     * del árbol binario de búsqueda.
     * @return 
     */
    private String getCodigoGraphviz() {
        return "digraph grafica{\n" +
               "node [fixedsize=true,shape = circle,fillcolor=lightblue1, style=filled];\n"+
                getCodigoInterno()+
                "}\n";
    }

    
    private String getCodigoInterno() {
        String etiqueta;
        if(hijoIzquierdo==null && hijoDerecho==null){
            etiqueta="nodo"+id+" [ label =\""+dato+"\"];\n";
        }else{
            etiqueta="nodo"+id+" [ label =\""+dato+"\"];\n";
        }
        if(hijoIzquierdo!=null){
            etiqueta=etiqueta + hijoIzquierdo.getCodigoInterno() +
               "nodo"+id+":C0->nodo"+hijoIzquierdo.id+"\n";
        }
        if(hijoDerecho!=null){
            etiqueta=etiqueta + hijoDerecho.getCodigoInterno() +
               "nodo"+id+":C1->nodo"+hijoDerecho.id+"\n";                    
        }
        return etiqueta;
    }        
       
        
    }
}

