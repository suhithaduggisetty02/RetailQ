package retailiq.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retailiq.util.ResultFormatter;

public class SATConstraintService {

    // Literal: positive integer for variable x, negative for not x
    // Clause: list of literals (OR)
    // Formula: list of clauses (AND)
    static class Clause {
        int[] literals;
        Clause(int... literals) { this.literals = literals; }
    }

    public static void perform3SATSolver() {
        String[] varNames = {
            "x1: Include Wireless Audio Promo",
            "x2: Enable Express Delivery Carrier",
            "x3: Apply Tier-1 Bulk Rebate",
            "x4: Stock Premium Gaming SKU"
        };
        int numVars = 4;

        // Retail procurement clauses in 3-CNF
        List<Clause> formula = new ArrayList<>();
        formula.add(new Clause(1, 2, -3));   // (x1 OR x2 OR NOT x3)
        formula.add(new Clause(-1, 3, 4));   // (NOT x1 OR x3 OR x4)
        formula.add(new Clause(-2, -4, 1));  // (NOT x2 OR NOT x4 OR x1)
        formula.add(new Clause(2, -3, 4));   // (x2 OR NOT x3 OR x4)

        long startTime = System.nanoTime();

        int[] assignment = new int[numVars + 1]; // 0: unassigned, 1: true, -1: false
        boolean satisfiable = dpll(formula, assignment, 1, numVars);

        long elapsed = System.nanoTime() - startTime;

        ResultFormatter.printHeader(
            "3-SAT Constraint Solver (DPLL Algorithm)",
            "CO5 - NP-Completeness & Exact Reductions",
            "retailConstraintSolver()",
            formula.size() + " Retail Supplier Contract Rules across " + numVars + " Decision Variables"
        );

        System.out.println("Retail Supplier Constraints (3-CNF Formula):");
        for (int i = 0; i < formula.size(); i++) {
            Clause c = formula.get(i);
            StringBuilder sb = new StringBuilder("(");
            for (int j = 0; j < c.literals.length; j++) {
                int lit = c.literals[j];
                sb.append(lit > 0 ? "x" + lit : "¬x" + (-lit));
                if (j < c.literals.length - 1) sb.append(" ∨ ");
            }
            sb.append(")");
            System.out.printf("• Clause %d: %s%n", (i + 1), sb.toString());
        }

        System.out.println("\nConstraint Solver Verdict:");
        System.out.println("--------------------------");
        if (satisfiable) {
            System.out.println("STATUS: SATISFIABLE (All retail rules mutually compliant!)");
            System.out.println("\nFeasible Operational Configuration:");
            for (int v = 1; v <= numVars; v++) {
                boolean val = assignment[v] > 0;
                System.out.printf("• %-36s = %s%n", varNames[v - 1], val ? "TRUE (Enabled)" : "FALSE (Disabled)");
            }
        } else {
            System.out.println("STATUS: UNSATISFIABLE (Conflicting business constraints cannot all be satisfied)");
        }

        ResultFormatter.printFooter(
            elapsed,
            "NP-Complete: Worst Case O(2^N) / DPLL Pruned Exponential",
            "O(N) recursion assignment state",
            "Algorithm Lab"
        );
    }

    private static boolean dpll(List<Clause> formula, int[] assignment, int varIdx, int numVars) {
        // Check current status
        boolean allSatisfied = true;
        for (Clause c : formula) {
            boolean clauseSat = false;
            boolean hasUndetermined = false;

            for (int lit : c.literals) {
                int var = Math.abs(lit);
                int assignVal = assignment[var];
                if (assignVal != 0) {
                    if ((lit > 0 && assignVal == 1) || (lit < 0 && assignVal == -1)) {
                        clauseSat = true;
                        break;
                    }
                } else {
                    hasUndetermined = true;
                }
            }

            if (!clauseSat && !hasUndetermined) {
                return false; // Conflict found
            }
            if (!clauseSat) {
                allSatisfied = false;
            }
        }

        if (allSatisfied) return true;
        if (varIdx > numVars) return false;

        // Try assigning TRUE (1)
        assignment[varIdx] = 1;
        if (dpll(formula, assignment, varIdx + 1, numVars)) return true;

        // Try assigning FALSE (-1)
        assignment[varIdx] = -1;
        if (dpll(formula, assignment, varIdx + 1, numVars)) return true;

        // Backtrack
        assignment[varIdx] = 0;
        return false;
    }
}
