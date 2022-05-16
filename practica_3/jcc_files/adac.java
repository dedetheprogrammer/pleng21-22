/* adac.java */
/* Generated By:JavaCC: Do not edit this line. adac.java */
package traductor;
import lib.attributes.Attributes;
import lib.symbolTable.*;
import lib.symbolTable.exceptions.*;
import lib.symbolTable.Symbol.ParameterClass;
import lib.symbolTable.Symbol.Types;
import lib.tools.SemanticFunctions;
import lib.tools.SemanticFunctions.Operator;
import lib.tools.SemanticFunctions.Procedure;
import lib.tools.exceptions.*;
import java.util.ArrayList;

public class adac implements adacConstants {

        //Se declara la tabla de simbolos
        static SymbolTable st;
        static SemanticFunctions sf;

        static int errors = 0;

        //Funcion que a lo mejor hay que cambiar
        private static void initSymbolTable() {
                boolean b;
                String[] palsRes = {
                "var","const","escribir"
                };

                st.insertReservedWords(palsRes);
        }

        public static void panicMode(Token err, int type) {
                errors++;
                System.err.println("ERROR SINTACTICO: (" + err.beginLine + ","
                        + err.beginColumn + "): " + err);
                System.err.println("----> Iniciando recuperacion en modo panico..."
                        + "\n----> Saltando todo hasta token de conjunto de sincronizacion");
                Token t = getNextToken();
                if (type == 0) {
                        while(t.kind != EOF) {
                                System.err.println("Descartando token ("
                                        + adacConstants.tokenImage[t.kind] + "," + t.image + ")");
                                t = getNextToken();
                        }
                } else {
                        while(t.kind != SCOLON && t.kind != END && t.kind != EOF) {
                                System.err.println("Descartando token ("
                                        + adacConstants.tokenImage[t.kind] + "," + t.image + ")");
                                t = getNextToken();
                        }
                }
        }


    public static void main(String[] args) throws java.io.FileNotFoundException,
                        ParseException
        {
        adac parser;

        try {
                        //Se iniciar y crea la tabla de simbolos pero esta vacia
                        st = new SymbolTable();
                initSymbolTable();
                        sf = new SemanticFunctions(st);

                        // Entrada desde stdin.
                if(args.length == 0) {
                                parser = new adac(System.in);
                        }
                        // Entrada desde fichero en args[0].
                        else {
                    parser = new adac(new java.io.FileInputStream(args[0]));
                        }
                        // Invoca símbolo inicial de la gramática.
                        parser.main();
                        if (errors == 0 && sf.getErrorSemantico().getErrors() == 0) System.out.println("Compilacion con exito.");
                        else System.out.println("Compilacion con errores.");
                } catch (java.io.FileNotFoundException e) {
                        System.err.println ("Fichero " + args[0] + " no encontrado.");
                } catch (TokenMgrError e) {
                System.err.println("LEX_ERROR: " + e.getMessage());
        }
    }

//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// ANALIZADOR SINTATICO & SEMANTICO.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Declaracion del procedimiento principal del fichero.
  static final public void main() throws ParseException {Attributes main = new Attributes(Types.PROCEDURE, Types.UNDEFINED, null, true);
        Token t;
    try {
      jj_consume_token(PROC);
      t = jj_consume_token(ID);
sf.AddMethod(main, t);
      jj_consume_token(IS);
      vars_def();
      procs_funcs_decl();
      proc_func_body(main);
      jj_consume_token(0);
    } catch (ParseException e) {
panicMode(e.currentToken.next, 0);
    }
}

//-----------------------------------------------------------------------------
// Declaracion de procedimientos/funciones del fichero.
  static final public void procs_funcs_decl() throws ParseException {
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case FUNC:
      case PROC:{
        ;
        break;
        }
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      proc_func_decl();
    }
}

//-----------------------------------------------------------------------------
// Declaracion de procedimiento/funcion
  static final public void proc_func_decl() throws ParseException {Attributes method = new Attributes();
        Token t;
    proc_or_func(method);
    func_return(method);
    t = jj_consume_token(ID);
sf.AddMethod(method, t);
    jj_consume_token(LPAREN);
    params_def(method.params);
    jj_consume_token(RPAREN);
    jj_consume_token(IS);
    vars_def();
    procs_funcs_decl();
    proc_func_body(method);
}

//-----------------------------------------------------------------------------
// Declaracion de si es procedimiento o funcion.
  static final public void proc_or_func(Attributes method) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case PROC:{
      jj_consume_token(PROC);
method.type = Types.PROCEDURE;
      break;
      }
    case FUNC:{
      jj_consume_token(FUNC);
method.type = Types.FUNCTION;
      break;
      }
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

//-----------------------------------------------------------------------------
// Tipo de dato que devuelve la funcion.
  static final public void func_return(Attributes method) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INT:
    case BOOL:
    case CHAR:{
      vars_type(method);
sf.EvaluateReturnHeader(method, Types.FUNCTION);
      break;
      }
    default:
      jj_la1[2] = jj_gen;

sf.EvaluateReturnHeader(method, Types.PROCEDURE);
    }
}

//-----------------------------------------------------------------------------
// Parametros de procedimiento/funcion.
  static final public void params_def(ArrayList<Symbol> params) throws ParseException {Attributes par = new Attributes(params);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case VAL:
    case REF:{
      param_class(par);
      vars_decl(par);
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case SCOLON:{
          ;
          break;
          }
        default:
          jj_la1[3] = jj_gen;
          break label_2;
        }
        jj_consume_token(SCOLON);
        param_class(par);
        vars_decl(par);
      }
      break;
      }
    default:
      jj_la1[4] = jj_gen;
      ;
    }
}

//-----------------------------------------------------------------------------
// Clase del parametro: por valor o por referencia.
  static final public void param_class(Attributes par) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case VAL:{
      jj_consume_token(VAL);
par.parClass = ParameterClass.VAL;
      break;
      }
    case REF:{
      jj_consume_token(REF);
par.parClass = ParameterClass.REF;
      break;
      }
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

//-----------------------------------------------------------------------------
// Variables de procedimiento/funcion.
  static final public void vars_def() throws ParseException {Attributes var = new Attributes(ParameterClass.NONE);
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case INT:
      case BOOL:
      case CHAR:{
        ;
        break;
        }
      default:
        jj_la1[6] = jj_gen;
        break label_3;
      }
      vars_decl(var);
      jj_consume_token(SCOLON);
    }
}

//-----------------------------------------------------------------------------
// Tipos de variable y variables asociadas.
  static final public void vars_decl(Attributes var) throws ParseException {
    vars_type(var);
    vars_list(var);
}

//-----------------------------------------------------------------------------
// Tipos de una variable.
  static final public void vars_type(Attributes var) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INT:{
      jj_consume_token(INT);
var.baseType = Types.INT;
      break;
      }
    case BOOL:{
      jj_consume_token(BOOL);
var.baseType = Types.BOOL;
      break;
      }
    case CHAR:{
      jj_consume_token(CHAR);
var.baseType = Types.CHAR;
      break;
      }
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

//-----------------------------------------------------------------------------
// Variables del mismo tipo.
  static final public void vars_list(Attributes var) throws ParseException {
    var(var);
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case COLON:{
        ;
        break;
        }
      default:
        jj_la1[8] = jj_gen;
        break label_4;
      }
      jj_consume_token(COLON);
      var(var);
    }
}

//-----------------------------------------------------------------------------
// Nombre de la variable.
  static final public void var(Attributes var) throws ParseException {Token t, ind;
    if (jj_2_1(2)) {
      t = jj_consume_token(ID);
      jj_consume_token(LBRACK);
      ind = jj_consume_token(INTVAL);
      jj_consume_token(RBRACK);
sf.AddVar(var, t, ind, Types.ARRAY);
    } else {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ID:{
        t = jj_consume_token(ID);
sf.AddVar(var, t, null, Types.UNDEFINED);
        break;
        }
      default:
        jj_la1[9] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
}

//-----------------------------------------------------------------------------
// Cuerpo de procedimiento/funcion.
  static final public void proc_func_body(Attributes header) throws ParseException {
    try {
      jj_consume_token(BEGIN);
      instructions_list(header);
      jj_consume_token(END);

    } catch (ParseException e) {
panicMode(e.currentToken.next, 1);
    }
}

//-----------------------------------------------------------------------------
// Conjunto de instrucciones.
  static final public void instructions_list(Attributes at) throws ParseException {
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case RETURN:
      case PUT:
      case PUTLINE:
      case SKIPLINE:
      case GET:
      case IF:
      case WHILE:
      case ID:{
        ;
        break;
        }
      default:
        jj_la1[10] = jj_gen;
        break label_5;
      }
      instruction(at);
    }
}

//-----------------------------------------------------------------------------
// Instruccion:
//	- Procedimientos predefinidos: get, put, putline, skipline.
//  - Procedimientos del usuario.
//	- Asignacion.
//	- While/If-Else.
//	- Return.
  static final public void instruction(Attributes at) throws ParseException {Attributes fst = new Attributes(), snd = new Attributes();
        Token t;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case GET:{
      t = jj_consume_token(GET);
      jj_consume_token(LPAREN);
      assignable(fst);
sf.EvaluateGet(t, fst.baseType);
      label_6:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case COLON:{
          ;
          break;
          }
        default:
          jj_la1[11] = jj_gen;
          break label_6;
        }
        jj_consume_token(COLON);
        assignable(fst);
sf.EvaluateGet(t, fst.baseType);
      }
      jj_consume_token(RPAREN);
      jj_consume_token(SCOLON);
      break;
      }
    case PUT:{
      t = jj_consume_token(PUT);
      jj_consume_token(LPAREN);
      expression(fst);
sf.EvaluatePut(t, fst.baseType);
      label_7:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case COLON:{
          ;
          break;
          }
        default:
          jj_la1[12] = jj_gen;
          break label_7;
        }
        jj_consume_token(COLON);
        expression(fst);
sf.EvaluatePut(t, fst.baseType);
      }
      jj_consume_token(RPAREN);
      jj_consume_token(SCOLON);
      break;
      }
    case PUTLINE:{
      jj_consume_token(PUTLINE);
      jj_consume_token(LPAREN);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case LPAREN:
      case INTVAL:
      case CHARVAL:
      case BOOLVAL:
      case STRING:
      case ADD:
      case SUB:
      case NOT:
      case CHAR2INT:
      case INT2CHAR:
      case ID:{
        expression(fst);
sf.EvaluatePutline(t, fst.baseType);
        label_8:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case COLON:{
            ;
            break;
            }
          default:
            jj_la1[13] = jj_gen;
            break label_8;
          }
          jj_consume_token(COLON);
          expression(fst);
sf.EvaluatePutline(t, fst.baseType);
        }
        break;
        }
      default:
        jj_la1[14] = jj_gen;
        ;
      }
      jj_consume_token(RPAREN);
      jj_consume_token(SCOLON);
      break;
      }
    case SKIPLINE:{
      jj_consume_token(SKIPLINE);
      jj_consume_token(LPAREN);
      jj_consume_token(RPAREN);
      jj_consume_token(SCOLON);
      break;
      }
    default:
      jj_la1[18] = jj_gen;
      if (jj_2_2(2)) {
        t = jj_consume_token(ID);
fst.name = t.image;
        jj_consume_token(LPAREN);
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case LPAREN:
        case INTVAL:
        case CHARVAL:
        case BOOLVAL:
        case STRING:
        case ADD:
        case SUB:
        case NOT:
        case CHAR2INT:
        case INT2CHAR:
        case ID:{
          expression(snd);
fst.given.add(snd);
          label_9:
          while (true) {
            switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
            case COLON:{
              ;
              break;
              }
            default:
              jj_la1[15] = jj_gen;
              break label_9;
            }
            jj_consume_token(COLON);
snd = new Attributes();
            expression(snd);
fst.given.add(snd);
          }
          break;
          }
        default:
          jj_la1[16] = jj_gen;
          ;
        }
        jj_consume_token(RPAREN);
        jj_consume_token(SCOLON);
sf.EvaluateProcedure(fst, t);
      } else {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case ID:{
          assignable(fst);
          jj_consume_token(ASS);
          expression(snd);
sf.EvaluateExpression(fst,snd);
          jj_consume_token(SCOLON);
          break;
          }
        case WHILE:{
          jj_consume_token(WHILE);
          expression(fst);
sf.EvaluateExpression(fst, Types.BOOL, 0);
          jj_consume_token(DO);
          instructions_list(at);
          jj_consume_token(END);
          break;
          }
        case IF:{
          jj_consume_token(IF);
          expression(fst);
sf.EvaluateExpression(fst, Types.BOOL, 1);
          jj_consume_token(THEN);
          instructions_list(at);
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case ELSE:{
            jj_consume_token(ELSE);
            instructions_list(at);
            break;
            }
          default:
            jj_la1[17] = jj_gen;
            ;
          }
          jj_consume_token(END);
          break;
          }
        case RETURN:{
          jj_consume_token(RETURN);
          expression(fst);
sf.comprobarReturnIf(at,fst);
          jj_consume_token(SCOLON);
          break;
          }
        default:
          jj_la1[19] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
}

//-----------------------------------------------------------------------------
// Elementos asignables.
  static final public void assignable(Attributes at) throws ParseException {Token t;
    if (jj_2_3(2)) {
      t = jj_consume_token(ID);
      jj_consume_token(LBRACK);
      expression(at);
      jj_consume_token(RBRACK);
sf.EvaluateAssignable(at, t, Types.ARRAY);
    } else {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ID:{
        t = jj_consume_token(ID);
sf.EvaluateAssignable(at, t, Types.UNDEFINED);
        break;
        }
      default:
        jj_la1[20] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
}

//-----------------------------------------------------------------------------
// Expresion relacional.
  static final public void expression(Attributes at) throws ParseException {Attributes fst = new Attributes();
        Attributes snd = new Attributes();
    simple_expr(fst);
at.name = fst.name; at.baseType = fst.baseType; at.parClass = fst.parClass;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case EQ:
    case NE:
    case LT:
    case GT:
    case LE:
    case GE:{
      relational_op(fst);
      simple_expr(snd);
sf.EvaluateExpression(at, fst, snd);
      break;
      }
    default:
      jj_la1[21] = jj_gen;
      ;
    }
}

//-----------------------------------------------------------------------------
// Expresion aritmetica.
  static final public void simple_expr(Attributes at) throws ParseException {Attributes fst = new Attributes();
        Attributes snd = new Attributes();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ADD:
    case SUB:{
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ADD:{
        jj_consume_token(ADD);
        break;
        }
      case SUB:{
        jj_consume_token(SUB);
        break;
        }
      default:
        jj_la1[22] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      break;
      }
    default:
      jj_la1[23] = jj_gen;
      ;
    }
    term(fst);
at.name = fst.name; at.baseType = fst.baseType; at.parClass = fst.parClass;
    label_10:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ADD:
      case SUB:
      case OR:{
        ;
        break;
        }
      default:
        jj_la1[24] = jj_gen;
        break label_10;
      }
      additive_op(fst);
      term(snd);
sf.EvaluateExpression(at, fst, snd);
    }
}

//-----------------------------------------------------------------------------
// Expresion multiplicativa.
  static final public void term(Attributes at) throws ParseException {Attributes fst = new Attributes();
        Attributes snd = new Attributes();
    factor(fst);
at.name = fst.name; at.baseType = fst.baseType; at.parClass = fst.parClass;
    label_11:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case MUL:
      case DIV:
      case MOD:
      case AND:{
        ;
        break;
        }
      default:
        jj_la1[25] = jj_gen;
        break label_11;
      }
      multiplicative_op(fst);
      factor(snd);
sf.EvaluateExpression(at, fst, snd);
    }
}

  static final public void factor(Attributes at) throws ParseException {Attributes fst = new Attributes();
        Attributes snd = new Attributes();
        int i = 0;
        SymbolFunction s = null;
        Token t;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NOT:{
      jj_consume_token(NOT);
      factor(at);
sf.EvaluateExpression(at);
      break;
      }
    case LPAREN:{
      jj_consume_token(LPAREN);
      expression(at);
      jj_consume_token(RPAREN);
      break;
      }
    case INT2CHAR:{
      jj_consume_token(INT2CHAR);
      jj_consume_token(LPAREN);
      expression(fst);
sf.CheckInt2Char(at, fst);
      jj_consume_token(RPAREN);
      break;
      }
    case CHAR2INT:{
      jj_consume_token(CHAR2INT);
      jj_consume_token(LPAREN);
      expression(fst);
sf.CheckChar2Int(at, fst);
      jj_consume_token(RPAREN);
      break;
      }
    default:
      jj_la1[28] = jj_gen;
      if (jj_2_4(2)) {
        t = jj_consume_token(ID);
fst.name = t.image;
        jj_consume_token(LPAREN);
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case LPAREN:
        case INTVAL:
        case CHARVAL:
        case BOOLVAL:
        case STRING:
        case ADD:
        case SUB:
        case NOT:
        case CHAR2INT:
        case INT2CHAR:
        case ID:{
          expression(snd);
fst.given.add(snd);
          label_12:
          while (true) {
            switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
            case COLON:{
              ;
              break;
              }
            default:
              jj_la1[26] = jj_gen;
              break label_12;
            }
            jj_consume_token(COLON);
snd = new Attributes();
            expression(snd);
fst.given.add(snd);
          }
          break;
          }
        default:
          jj_la1[27] = jj_gen;
          ;
        }
        jj_consume_token(RPAREN);
sf.EvaluateFunction(st, at, fst);
      } else if (jj_2_5(2)) {
        t = jj_consume_token(ID);
        jj_consume_token(LBRACK);
        expression(fst);
        jj_consume_token(RBRACK);
sf.CheckExpression(st, at, t, Types.ARRAY);
                        //System.err.println("El token se llama " + t.image);
                        //System.err.println("Me llega el atributte at " + at.name);
                        //System.err.println("Me llega el atributte fst " + fst.name);

      } else {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case ID:{
          t = jj_consume_token(ID);
//System.err.println("Me llega el token " + t.image);
                                                //System.err.println("Me llega el at " + at.name);
                                                sf.CheckExpression(st, at, t, Types.UNDEFINED);
          break;
          }
        case INTVAL:{
          t = jj_consume_token(INTVAL);
at.initInt(t.image);
          break;
          }
        case CHARVAL:{
          t = jj_consume_token(CHARVAL);
at.initChar(t.image);
          break;
          }
        case BOOLVAL:{
          t = jj_consume_token(BOOLVAL);
at.initBool(t.image);
          break;
          }
        case STRING:{
          t = jj_consume_token(STRING);
at.initString(t.image);
          break;
          }
        default:
          jj_la1[29] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
}

  static final public void relational_op(Attributes at) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case EQ:{
      jj_consume_token(EQ);
      break;
      }
    case LT:{
      jj_consume_token(LT);
      break;
      }
    case GT:{
      jj_consume_token(GT);
      break;
      }
    case LE:{
      jj_consume_token(LE);
      break;
      }
    case GE:{
      jj_consume_token(GE);
      break;
      }
    case NE:{
      jj_consume_token(NE);
      break;
      }
    default:
      jj_la1[30] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
at.op = Operator.CMP_OP;
}

  static final public void additive_op(Attributes at) throws ParseException {Token t;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ADD:
    case SUB:{
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ADD:{
        jj_consume_token(ADD);
        break;
        }
      case SUB:{
        jj_consume_token(SUB);
        break;
        }
      default:
        jj_la1[31] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
at.op = Operator.INT_OP;
      break;
      }
    case OR:{
      jj_consume_token(OR);
at.op = Operator.BOOL_OP;
      break;
      }
    default:
      jj_la1[32] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  static final public void multiplicative_op(Attributes at) throws ParseException {Token t;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case MUL:
    case DIV:
    case MOD:{
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case MUL:{
        jj_consume_token(MUL);
        break;
        }
      case MOD:{
        jj_consume_token(MOD);
        break;
        }
      case DIV:{
        jj_consume_token(DIV);
        break;
        }
      default:
        jj_la1[33] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
at.op = Operator.INT_OP;
      break;
      }
    case AND:{
      jj_consume_token(AND);
at.op = Operator.BOOL_OP;
      break;
      }
    default:
      jj_la1[34] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  static private boolean jj_2_1(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_1()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  static private boolean jj_2_2(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_2()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  static private boolean jj_2_3(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_3()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  static private boolean jj_2_4(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_4()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(3, xla); }
  }

  static private boolean jj_2_5(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_5()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(4, xla); }
  }

  static private boolean jj_3_5()
 {
    if (jj_scan_token(ID)) return true;
    if (jj_scan_token(LBRACK)) return true;
    return false;
  }

  static private boolean jj_3_3()
 {
    if (jj_scan_token(ID)) return true;
    if (jj_scan_token(LBRACK)) return true;
    return false;
  }

  static private boolean jj_3_2()
 {
    if (jj_scan_token(ID)) return true;
    if (jj_scan_token(LPAREN)) return true;
    return false;
  }

  static private boolean jj_3_4()
 {
    if (jj_scan_token(ID)) return true;
    if (jj_scan_token(LPAREN)) return true;
    return false;
  }

  static private boolean jj_3_1()
 {
    if (jj_scan_token(ID)) return true;
    if (jj_scan_token(LBRACK)) return true;
    return false;
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public adacTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private Token jj_scanpos, jj_lastpos;
  static private int jj_la;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[35];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
	   jj_la1_init_0();
	   jj_la1_init_1();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0x0,0x0,0x38000,0x200,0x0,0x0,0x38000,0x38000,0x400,0x0,0x0,0x400,0x400,0x400,0x1bc0800,0x400,0x1bc0800,0x0,0x0,0x0,0x0,0xf0000000,0x1800000,0x1800000,0x1800000,0xe000000,0x400,0x1bc0800,0x800,0x3c0000,0xf0000000,0x1800000,0x1800000,0xe000000,0xe000000,};
	}
	private static void jj_la1_init_1() {
	   jj_la1_1 = new int[] {0x60,0x60,0x0,0x0,0x300,0x300,0x0,0x0,0x0,0x1000000,0x1139c00,0x0,0x0,0x0,0x1006010,0x0,0x1006010,0x80000,0x19800,0x1120400,0x1000000,0x3,0x0,0x0,0x8,0x4,0x0,0x1006010,0x6010,0x1000000,0x3,0x0,0x8,0x0,0x4,};
	}
  static final private JJCalls[] jj_2_rtns = new JJCalls[5];
  static private boolean jj_rescan = false;
  static private int jj_gc = 0;

  /** Constructor with InputStream. */
  public adac(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public adac(java.io.InputStream stream, String encoding) {
	 if (jj_initialized_once) {
	   System.out.println("ERROR: Second call to constructor of static parser.  ");
	   System.out.println("	   You must either use ReInit() or set the JavaCC option STATIC to false");
	   System.out.println("	   during parser generation.");
	   throw new Error();
	 }
	 jj_initialized_once = true;
	 try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new adacTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 35; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 35; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public adac(java.io.Reader stream) {
	 if (jj_initialized_once) {
	   System.out.println("ERROR: Second call to constructor of static parser. ");
	   System.out.println("	   You must either use ReInit() or set the JavaCC option STATIC to false");
	   System.out.println("	   during parser generation.");
	   throw new Error();
	 }
	 jj_initialized_once = true;
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new adacTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 35; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new SimpleCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new adacTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 35; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public adac(adacTokenManager tm) {
	 if (jj_initialized_once) {
	   System.out.println("ERROR: Second call to constructor of static parser. ");
	   System.out.println("	   You must either use ReInit() or set the JavaCC option STATIC to false");
	   System.out.println("	   during parser generation.");
	   throw new Error();
	 }
	 jj_initialized_once = true;
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 35; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(adacTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 35; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   if (++jj_gc > 100) {
		 jj_gc = 0;
		 for (int i = 0; i < jj_2_rtns.length; i++) {
		   JJCalls c = jj_2_rtns[i];
		   while (c != null) {
			 if (c.gen < jj_gen) c.first = null;
			 c = c.next;
		   }
		 }
	   }
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }

  @SuppressWarnings("serial")
  static private final class LookaheadSuccess extends java.lang.Error {
    @Override
    public Throwable fillInStackTrace() {
      return this;
    }
  }
  static private final LookaheadSuccess jj_ls = new LookaheadSuccess();
  static private boolean jj_scan_token(int kind) {
	 if (jj_scanpos == jj_lastpos) {
	   jj_la--;
	   if (jj_scanpos.next == null) {
		 jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
	   } else {
		 jj_lastpos = jj_scanpos = jj_scanpos.next;
	   }
	 } else {
	   jj_scanpos = jj_scanpos.next;
	 }
	 if (jj_rescan) {
	   int i = 0; Token tok = token;
	   while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
	   if (tok != null) jj_add_error_token(kind, i);
	 }
	 if (jj_scanpos.kind != kind) return true;
	 if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
	 return false;
  }


/** Get the next Token. */
  static final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	 return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  static private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;
  static private int[] jj_lasttokens = new int[100];
  static private int jj_endpos;

  static private void jj_add_error_token(int kind, int pos) {
	 if (pos >= 100) {
		return;
	 }

	 if (pos == jj_endpos + 1) {
	   jj_lasttokens[jj_endpos++] = kind;
	 } else if (jj_endpos != 0) {
	   jj_expentry = new int[jj_endpos];

	   for (int i = 0; i < jj_endpos; i++) {
		 jj_expentry[i] = jj_lasttokens[i];
	   }

	   for (int[] oldentry : jj_expentries) {
		 if (oldentry.length == jj_expentry.length) {
		   boolean isMatched = true;

		   for (int i = 0; i < jj_expentry.length; i++) {
			 if (oldentry[i] != jj_expentry[i]) {
			   isMatched = false;
			   break;
			 }

		   }
		   if (isMatched) {
			 jj_expentries.add(jj_expentry);
			 break;
		   }
		 }
	   }

	   if (pos != 0) {
		 jj_lasttokens[(jj_endpos = pos) - 1] = kind;
	   }
	 }
  }

  /** Generate ParseException. */
  static public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[58];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 35; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		   if ((jj_la1_1[i] & (1<<j)) != 0) {
			 la1tokens[32+j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 58; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 jj_endpos = 0;
	 jj_rescan_token();
	 jj_add_error_token(0, 0);
	 int[][] exptokseq = new int[jj_expentries.size()][];
	 for (int i = 0; i < jj_expentries.size(); i++) {
	   exptokseq[i] = jj_expentries.get(i);
	 }
	 return new ParseException(token, exptokseq, tokenImage);
  }

  static private boolean trace_enabled;

/** Trace enabled. */
  static final public boolean trace_enabled() {
	 return trace_enabled;
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

  static private void jj_rescan_token() {
	 jj_rescan = true;
	 for (int i = 0; i < 5; i++) {
	   try {
		 JJCalls p = jj_2_rtns[i];

		 do {
		   if (p.gen > jj_gen) {
			 jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
			 switch (i) {
			   case 0: jj_3_1(); break;
			   case 1: jj_3_2(); break;
			   case 2: jj_3_3(); break;
			   case 3: jj_3_4(); break;
			   case 4: jj_3_5(); break;
			 }
		   }
		   p = p.next;
		 } while (p != null);

		 } catch(LookaheadSuccess ls) { }
	 }
	 jj_rescan = false;
  }

  static private void jj_save(int index, int xla) {
	 JJCalls p = jj_2_rtns[index];
	 while (p.gen > jj_gen) {
	   if (p.next == null) { p = p.next = new JJCalls(); break; }
	   p = p.next;
	 }

	 p.gen = jj_gen + xla - jj_la; 
	 p.first = token;
	 p.arg = xla;
  }

  static final class JJCalls {
	 int gen;
	 Token first;
	 int arg;
	 JJCalls next;
  }

}
