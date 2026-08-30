# Data Structures & Algorithms

Código deste projeto referente ao curso da Udemy:

**[Data Structures & Algorithms](https://www.udemy.com/course/draft/1330262/learn/lecture/14237796?start=0#overview)**

## Sobre

Projeto Maven acompanhando as aulas do curso. O código está organizado por
**seções**, seguindo a estrutura do curso, em `src/main/java/`.

## Seções

| Seção | Tópico | Pacote |
| --- | --- | --- |
| 04 | Arrays | `section4/arrays` |
| 05 | Listas Ligadas (Simples, Dupla, Circular) | `section5/linkedlist` |
| 06 | Pilha (Stack — por array e por lista ligada) | `section6/stack` |
| 07 | Fila (Queue) | `section7/queue` |
| 08 | Árvore Binária (Binary Tree) | `section8/binaryTree` |
| 09 | Árvore Binária de Busca (BST) | `section9/binarySearchTree` |
| 10 | Árvore AVL | `section10/AVLTree` |
| 11 | Heap Binário | `section11/heap` |
| 12 | Trie | `section12/trie` |
| 13 | Hashing (Chaining, Linear/Quadratic/Double Probing) | `section13/hashing` |
| 14 | Ordenação (Bubble, Bucket, Insertion, Merge, Quick, Selection) | `section14/sorting` |

> Os slides/notas por seção estão em `DataStructure_Algorithms/`.

## Como executar

Projeto Maven (Java). Cada tópico tem sua classe `*Main` para rodar.

```bash
mvn compile
# rode a classe Main de uma seção, ex.:
mvn exec:java -Dexec.mainClass="section14.sorting.mergesort.MergeSortMain"
```

Ou abra a pasta no IntelliJ IDEA e rode a classe `*Main` da seção desejada.
