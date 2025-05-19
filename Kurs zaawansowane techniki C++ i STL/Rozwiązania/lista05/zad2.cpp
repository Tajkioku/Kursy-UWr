#include <iostream>
#include <unordered_map>
#include <vector>
#include <queue>
#include <limits>

struct Edge {
    std::string target;
    double weight;

    Edge(const std::string& tgt, double w) : target(tgt), weight(w) {}
};

class WeightedGraph {
public:
    // Dodaj wierzcholek
    void addVertex(const std::string& vertex) {
        if (vertex.empty()) {
            std::cerr << "Bledna nazwa wierzcholka." << std::endl;
            return;
        }

        if (vertices.find(vertex) == vertices.end()) {
            vertices[vertex] = std::vector<Edge>();
        }
    }

    // Dodaj krawedz
    void addEdge(const std::string& src, const std::string& tgt, double weight) {
        addVertex(src);
        addVertex(tgt);

        // Sprawdz czy juz istnieje
        for (const auto& edge : vertices[src]) {
            if (edge.target == tgt) {
                std::cerr << "Krawedz juz istnieje." << std::endl;
                return;
            }
        }

        vertices[src].emplace_back(tgt, weight);
        // vertices[tgt].emplace_back(src, weight);  // Graf nieskierowany
    }

    // Usun wierzcholek
    void removeVertex(const std::string& vertex) {
        vertices.erase(vertex);

        // Usun wszystkie krawedzie podpiete do tego wierzcholkiem
        for (auto& entry : vertices) {
            auto& edges = entry.second;
            edges.erase(std::remove_if(edges.begin(), edges.end(),
                [vertex](const Edge& edge) { return edge.target == vertex; }),
                edges.end());
        }
    }

    // Usun krawedz
    void removeEdge(const std::string& src, const std::string& tgt) {
        auto& edges = vertices[src];
        edges.erase(std::remove_if(edges.begin(), edges.end(),
            [tgt](const Edge& edge) { return edge.target == tgt; }),
            edges.end());

        // // Graf nieskierowany
        // auto& oppositeEdges = vertices[tgt];
        // oppositeEdges.erase(std::remove_if(oppositeEdges.begin(), oppositeEdges.end(),
        //     [src](const Edge& edge) { return edge.target == src; }),
        //     oppositeEdges.end());
    }

    // Aktualizuj wage
    void updateEdgeWeight(const std::string& src, const std::string& tgt, double newWeight) {
        for (auto& edge : vertices[src]) {
            if (edge.target == tgt) {
                edge.weight = newWeight;
                break;
            }
        }

        // // Graf nieskierowany
        // for (auto& edge : vertices[tgt]) {
        //     if (edge.target == src) {
        //         edge.weight = newWeight;
        //         break;
        //     }
        // }
    }

    // Wysietl graf
    void display() const {
        for (const auto& entry : vertices) {
            std::cout << entry.first << " -> ";
            for (const auto& edge : entry.second) {
                std::cout << "(" << edge.target << ", " << edge.weight << ") ";
            }
            std::cout << std::endl;
        }
    }

private:
    // Lista sasiadow
    std::unordered_map<std::string, std::vector<Edge>> vertices;
};

int main() {
    WeightedGraph graph;

    graph.addVertex("A");
    graph.addVertex("B");
    graph.addVertex("C");
    graph.addVertex("D");

    graph.addEdge("A", "B", 2.0);
    graph.addEdge("A", "C", 1.0);
    graph.addEdge("B", "C", 3.0);
    graph.addEdge("B", "D", 1.0);

    std::cout << "Stan poczatkowy:" << std::endl;
    graph.display();

    // Usuwanie C i krawedzi
    graph.removeVertex("C");
    std::cout << "\nGraf po usunieciu C:" << std::endl;
    graph.display();

    // Dodawanie E i krawedzi D-E
    graph.addVertex("E");
    graph.addEdge("D", "E", 4.0);
    std::cout << "\nGraf po dodaniu E i D-E:" << std::endl;
    graph.display();

    // Aktualizacja wagi A-B
    graph.updateEdgeWeight("A", "B", 5.0);
    std::cout << "\nGraf po zmianie wagi A-B na 5.0:" << std::endl;
    graph.display();

    return 0;
}
