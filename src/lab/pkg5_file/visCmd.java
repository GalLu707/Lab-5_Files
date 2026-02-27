/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.pkg5_file;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author USER
 */
public class visCmd extends JFrame {
    
    private final JTextArea area;
    private int inicioEntrada;
    private File rutaActual;
    private ManejodeArchivos gestorArchivos;
    
    public visCmd(){
        super("CMD sistema");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        
        area = new JTextArea();
        area.setVisible(true);
        area.setBackground(Color.BLACK);
        area.setForeground(Color.WHITE);
        area.setCaretColor(Color.WHITE);
        area.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        add(new JScrollPane(area));
        
        PrintStream printStream = new PrintStream(new CustomOutputStream(area));
        System.setOut(printStream);
        System.setErr(printStream);
        
        area.addKeyListener(new KeyAdapter(){
            
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    e.consume();
                    ejecutarComando();
                }
                if(e.getKeyCode()== KeyEvent.VK_BACK_SPACE){
                    if(area.getCaretPosition()<= inicioEntrada){
                        e.consume();
                    }
                }
            }
            
        });
        
        String dirUsuario = System.getProperty("user.dir");
        rutaActual = new File(dirUsuario);
        gestorArchivos = new ManejodeArchivos(dirUsuario);
        
        promptInicial();
        
        
    }
    
    private void promptInicial(){
    
        System.out.println("Microsoft Windows\n");
        System.out.println("(c) Microsoft Corporation. Todos los derechos reservados.\\n\\n");
        System.out.println("Si ocupas ayuda usa el comando 'help'.\\n\\n");
        prompt();
    }
    
    private void prompt(){
        System.out.println(rutaActual.getAbsolutePath() + ">");
        inicioEntrada = area.getDocument().getLength();
    }
    
    private void ejecutarComando(){
        String textoCompleto = area.getText();
        String textoComando = textoCompleto.substring(inicioEntrada).trim();
        System.out.println("\n");
        
        if(textoComando.isEmpty()){
            prompt();
            return;
        }
    String[] partes = textoComando.split("\\s+", 2);
    String comando = partes[0].toLowerCase();
    String Argumento = partes.length>1?partes[1]: "";
    
    try {
            switch (comando) {
                case "exit": case "salir":
                    System.exit(0);
                    break;
                case "cls": case "limpiar":
                    area.setText("");
                    prompt();
                    return;
                case "echo":
                    System.out.println(Argumento);
                    break;
                case "date": case "fecha":
                    System.out.println("La fecha actual es: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    break;
                case "time": case "hora":
                    System.out.println("La hora actual es: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                    break;
                case "cd":
                    cambiarDirectorio(Argumento);
                    break;
                case "dir":
                    gestorArchivos.mostrarDir(Argumento);
                    break;
                case "mkdir":
                    gestorArchivos.mkdir(Argumento);
                    break;
                case "mfile":
                    gestorArchivos.mfile(Argumento);
                    break;
                case "rm":
                    gestorArchivos.rm(Argumento);
                    break;
                case "wr":
                    String[] argsWr = Argumento.split("\\s+", 2);
                    if (argsWr.length < 2) {
                        System.out.println("Uso incorrecto. Sintaxis: wr <archivo> <texto para escribir>");
                    } else {
                        gestorArchivos.wr(argsWr[0], argsWr[1]);
                    }
                    break;
                case "rd":
                    String contenido = gestorArchivos.rd(Argumento);
                    if (contenido != null) {
                        System.out.print(contenido);
                    }
                    break;
                case "help": case "ayuda":
                    imprimirayuda();
                    break;
                default:
                    System.out.println("'" + comando + "' no se reconoce como un comando interno o externo,\nprograma o archivo por lotes ejecutable.");
                    break;
            }
        } catch (IOException e) {
            System.out.println("Error de Entrada/Salida: " + e.getMessage());
        }

        prompt();
    
    
    
    
    
        
    }
    
    private void cambiarDirectorio(String ruta){
        if(ruta.isEmpty()){
            System.out.println(rutaActual.getAbsolutePath());
            return;
        }
        
        File nuevaRuta;
        if(ruta.equals("...")){
            nuevaRuta = rutaActual.getParentFile();
            
        }else{
            nuevaRuta = new File(ruta);
            if(!nuevaRuta.isAbsolute()){
                nuevaRuta = new File(rutaActual, ruta);
                
            }
        }
        if(nuevaRuta != null && nuevaRuta.exists() && nuevaRuta.isDirectory()){
            rutaActual= nuevaRuta;
            gestorArchivos.cd(rutaActual);
        }else{
            System.out.println("El sistema no puede encontrar la ruta especificada");
        }
    }
    
    private void imprimirayuda(){
        System.out.println("Comandos disponibles:");
        System.out.println("  CLS, LIMPIAR         Limpia la pantalla.");
        System.out.println("  DATE, FECHA          Muestra la fecha actual.");
        System.out.println("  TIME, HORA           Muestra la hora actual.");
        System.out.println("  DIR [directorio]     Muestra la lista de archivos y subdirectorios.");
        System.out.println("  CD <directorio>      Cambia de directorio (usa '..' para retroceder).");
        System.out.println("  MKDIR <directorio>   Crea un directorio.");
        System.out.println("  MFILE <archivo>      Crea un archivo vacío.");
        System.out.println("  RM <archivo/dir>     Elimina un archivo o directorio.");
        System.out.println("  WR <archivo> <texto> Escribe o sobrescribe texto en un archivo existente.");
        System.out.println("  RD <archivo>         Muestra el contenido de un archivo de texto.");
        System.out.println("  EXIT, SALIR          Cierra la consola.");
    }
    
    
}
