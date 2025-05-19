#include <iostream>
#include <string>
#include <memory>
#include <vector>
#include <set>

enum class Gender {
    Male,
    Female
};

class llama;

using LlamaPtr = std::shared_ptr<llama>;
using LlamaWeakPtr = std::weak_ptr<llama>;

class llama {
public:
    std::string name;
    Gender gender;
    LlamaPtr father;
    LlamaPtr mother;
    std::vector<LlamaWeakPtr> children;

    llama(const std::string &n, Gender g, LlamaPtr f = nullptr, LlamaPtr m = nullptr)
        : name(n), gender(g), father(f), mother(m) {
        std::cout << "Llama " << name << " dolaczyla do stada." << std::endl;
    }

    ~llama() {
        std::cout << "Llama " << name << " opuscila stado." << std::endl;
    }

    void addChild(const LlamaPtr &child) {
        children.push_back(child);
    }

    bool operator<(const llama &other) const {
        return name < other.name;
    }
};

class LlamaHerd {
private:
    std::set<LlamaPtr> herd;

public:
    LlamaPtr buyLlama(const std::string &name, Gender gender) {
        LlamaPtr newLlama = std::make_shared<llama>(name, gender);
        herd.insert(newLlama);
        return newLlama;
    }

    LlamaPtr birthLlama(const std::string &name, Gender gender, const LlamaPtr &father, const LlamaPtr &mother) {
        LlamaPtr child = std::make_shared<llama>(name, gender, father, mother);
        father->addChild(child);
        mother->addChild(child);
        herd.insert(child);
        return child;
    }

    void sellLlama(const LlamaPtr &llama) {
        herd.erase(llama);
    }

    size_t size() const {
        return herd.size();
    }
};

int main() {
    LlamaHerd herd;

    auto lola = herd.buyLlama("Lola", Gender::Female);
    auto luke = herd.buyLlama("Luke", Gender::Male);
    auto lana = herd.birthLlama("Lana", Gender::Female, luke, lola);

    std::cout << "Herd size: " << herd.size() << std::endl;

    herd.sellLlama(lola); 

    LlamaPtr retrieved_mother;
    if (lana->mother) {
        retrieved_mother = lana->mother;
        std::cout << "Lana's mother: " << retrieved_mother->name << std::endl;
    } else {
        std::cout << "Lana's mother is not in the herd anymore." << std::endl;
    }

    LlamaWeakPtr weak_child = lana; // Zmiana na weak pointer
    herd.sellLlama(lana); // Sprawdzam co sie stanie po sprzedaniu

    if (auto locked_child = weak_child.lock()) {
        std::cout << "Weak pointer is still valid." << std::endl;
    } else {
        std::cout << "Weak pointer is dangling." << std::endl;
    }

    return 0;
}
