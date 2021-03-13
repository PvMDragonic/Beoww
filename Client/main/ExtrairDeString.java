package main;

public class ExtrairDeString 
{
    private static String inverter(String texto)
    {
        String invertido = "";
        
        for (int i = texto.length() - 1; i >= 0; i--)
        {
            invertido = invertido + texto.charAt(i);
        }
        
        return invertido;
    }
    
    public static String extrair(String entrada, char delimitador)
    {
        String resultado = "";
        
        // Loop vai rodar por todos os chars, mas do final até o início.
        for (int i = 0; i < entrada.length(); i++) 
        {
            if (entrada.charAt((entrada.length() - 1) - i) == delimitador)
            {
                break;
            }
            else
            {
                resultado += entrada.charAt((entrada.length() - 1) - i);
            }
        }
        
        // Tem que inverter, porque tá ao contrário. (ex.: png -> gnp)
        return inverter(resultado);
    } 
}
