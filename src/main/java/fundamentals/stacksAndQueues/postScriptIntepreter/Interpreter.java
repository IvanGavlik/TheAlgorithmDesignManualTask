package fundamentals.stacksAndQueues.postScriptIntepreter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Stack;

public class Interpreter {
    Stack<PSO> exe = new Stack<>();
    ProgramData program = new ProgramDataImpl();
    public void exe(PSO pso) {
        this.exe.push(pso);
        if (pso.isLiteral()) {
            program.pushToOperand(pso);
        } else {
            // imam dva ista objekta pso jedan u dictionariju drugi from tokena
            exeUtil(program.getFromDictionary(pso.getName()));
        }
        this.exe.pop();
    }

    private void exeUtil(PSO pso) {
        if (pso instanceof PSOComplex) {
            ((PSOComplex) pso).getSimple().forEach(el -> exeUtil(el));
        }
        if (pso.isLiteral()) { // nece uvijek radit
            program.pushToOperand(pso);
            return;
        }
        if (pso.getName().equals("add")) {
            this.addition();
        }
        if (pso.getName().equals("sub")) {
            this.sub();
        }
        if (pso.getName().equals("mul")) {
            this.mul();
        }
        if (pso.getName().equals("div")) {
            this.div();
        }
        if (pso.getName().equals("mod")) {
            this.mod();
        }
        if (pso.getName().equals("abs")) {
            this.abs();
        }
        if (pso.getName().equals("neg")) {
            this.neg();
        }
        if (pso.getName().equals("neg")) {
            this.neg();
        }
        if (pso.getName().equals("sqrt")) {
            this.sqrt();
        }
        if (pso.getName().equals("print")) {
            this.print();
        }
    }

    private void addition() {
        program.pushToOperand(MathOperation.add(program.pullFromOperand(), program.pullFromOperand()));
    }
    private void sub() {
        program.pushToOperand(MathOperation.sub(program.pullFromOperand(), program.pullFromOperand()));
    }
    private void mul() {
        program.pushToOperand(MathOperation.mul(program.pullFromOperand(), program.pullFromOperand()));
    }
    private void div() {
        program.pushToOperand(MathOperation.div(program.pullFromOperand(), program.pullFromOperand()));
    }
    private void mod() {
        program.pushToOperand(MathOperation.mod(program.pullFromOperand(), program.pullFromOperand()));
    }
    private void abs() {
        program.pushToOperand(MathOperation.abs(program.pullFromOperand()));
    }
    private void neg() {
        program.pushToOperand(MathOperation.neg(program.pullFromOperand()));
    }
    private void sqrt() {
        program.pushToOperand(MathOperation.sqrt(program.pullFromOperand()));
    }
    private void def() {
        PSO value = program.pullFromOperand();
        value.setLiteral(false);
        PSO key = program.pullFromOperand();

        program.pushToCurrentDictionary(key.getData(), value);
    }
    private void print() {
        final String data = program.pullFromOperand().getData();
        if (Application.OUTPUT.isEmpty()) {
            System.out.println(data);
        } else {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(Application.OUTPUT));
                writer.write(data);
                writer.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public PSO getResult() {
        return program.pullFromOperand();
    }
}
