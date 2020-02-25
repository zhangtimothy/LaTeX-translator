# LaTeX-translator
Timothy Zhang

This is a personal project that I created to practice language parsing, RegEx, and LaTeX.
The program takes in a line of english from stdin and prints the line to stdout with certain patterns replaced by an 
associated LaTeX command. Interesting challenges arose when multiple commands contained the same phrase(such as "less 
than" and "less than or equal to"), and when dealing with fractions with multiple space-separated arguments in the 
numerator/denominator, or with nested fractions(such as "((a + b)/(c/d))"). It handles spacing discrepancies as well; 
for example, "a+b", "a + b", "a +b", and "a+ b" are all treated the same. To solve each of these problems, large 
changes had to be made to my algorithm.
 
Example uses:
 
Logic Translator, Math: Displayed 
Input: 
  for all x in the set of rational numbers, there exist some a, b in integers such that x = a/b 
Output: 
  $$\forall x \in \textbf{Q} , \exists  a , b \in \textbf{Z} \textrm{ s.t. } x = \frac{a}{b}  $$ 
  
Input: 
  (a+b)/(c/d) >= e and e is less than or equal to f 
Output: 
  $$\frac{ a+b }{ \frac{c}{d}  }  \geq e \land e  \leq f $$ 
