#include <iostream>
#include <string>
#include <type_traits>
#include <memory>

template<typename T>
struct on_stack {
    T object;
    T* operator->() { return &object; }
    T& operator*() { return object; }
};

template<typename T>
struct on_heap {
    on_heap() : ptr(new T()) {}
    ~on_heap() { delete ptr; }
    T* ptr;
    T* operator->() { return ptr; }
    T& operator*() { return *ptr; }
};

template <typename T>
struct obj_holder {
    // 'maly' obiekt <= sizeof(string) && nie jest tablica
    using type = typename std::conditional<
        sizeof(T) <= sizeof(std::string) && !std::is_array<T>::value,
        on_stack<T>,
        on_heap<T>
    >::type;
};


int main() {
    obj_holder<int>::type int_holder;
    *int_holder = 4;
    std::cout << "int_holder: " << *int_holder << std::endl;

    return 0;
}
