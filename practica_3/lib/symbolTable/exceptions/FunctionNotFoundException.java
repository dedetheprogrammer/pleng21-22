/*********************************************************************************
 * Excepción utilizada al intentar utilizar un símbolo no definido en
 * la tabla de símbolos
 *
 * Fichero:    SymbolNotFoundException.java
 * Fecha:      02/03/2022
 * Versión:    v1.1
 * Asignatura: Procesadores de Lenguajes, curso 2021-2022, basado en código del 19-20
 **********************************************************************************/

package lib.symbolTable.exceptions;

public class FunctionNotFoundException extends Error {

	public FunctionNotFoundException() {}

	@Override
    public String toString() {
        return "MENSAJE DE FunctionNotFoundException NO IMPLEMENTADO.";
    }
}