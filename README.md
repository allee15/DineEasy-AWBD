## **DineEasy - Backend**  
### **Despre proiect**  
DineEasy este o aplicație destinată utilizatorilor care doresc să rezerve mese la restaurante într-un mod simplu și rapid. Aplicația permite utilizatorilor să exploreze restaurantele disponibile, să vadă meniul, să rezerve o masă pentru un anumit interval orar și să primească o confirmare prin e-mail, fără a fi necesară crearea unui cont.  

---

### **Cerințe funcționale (Business Requirements)**  
:heavy_check_mark: Utilizatorii trebuie să poată vizualiza restaurantele disponibile.  
:heavy_check_mark: Utilizatorii trebuie să poată selecta un restaurant.  
:heavy_check_mark: Utilizatorii trebuie să poată vedea meniul fiecărui restaurant.  
:heavy_check_mark: Utilizatorii trebuie să poată face o rezervare pentru o anumită zi și oră.  
:heavy_check_mark: Utilizatorii trebuie să poată alege numărul de locuri pentru rezervare.  
:heavy_check_mark: Utilizatorii trebuie să primească o confirmare a rezervării pe e-mail.  
:heavy_check_mark: Utilizatorii trebuie să poată căuta restaurante după nume sau locație.  
:heavy_check_mark: Utilizatorii trebuie să poată filtra restaurantele după tipul bucătăriei (italiană, asiatică, vegan etc.).  
:heavy_check_mark: Utilizatorii trebuie să poată anula o rezervare înainte de ora programată.  
:heavy_check_mark: Restaurantul trebuie să poată vedea rezervările active.  

---

### **Detalii despre implementare**  
✔ Relațiile dintre entități vor fi create folosind: `@OneToOne`, `@OneToMany`, `@ManyToOne`, `@ManyToMany`.  
✔ Toate tipurile de operațiuni CRUD vor fi implementate.  
✔ Aplicația va fi testată cu profiluri diferite și două baze de date (una H2 pentru teste).  
✔ Se vor implementa **unit tests**.  
✔ Datele vor fi validate, iar excepțiile vor fi gestionate corespunzător.  
✔ Se vor folosi opțiuni pentru **paginare și sortare** a datelor.  
✔ Se va include Spring Security pentru autentificare minimă cu JDBC (opțional).  

---

### **Funcționalități principale**  
1️⃣ **Explorarea restaurantelor**  
   - Afișează lista de restaurante disponibile.  
   - Permite utilizatorilor să vadă informații despre restaurante (nume, locație, tip bucătărie).  

2️⃣ **Selectarea unui restaurant și vizualizarea meniului**  
   - Oferă detalii despre restaurant (program, adresa, recenzii).  
   - Prezintă meniul restaurantului cu prețuri și imagini.  

3️⃣ **Rezervarea unei mese**  
   - Permite utilizatorilor să aleagă ziua și ora rezervării.  
   - Afișează numărul de locuri disponibile.  
   - Confirmarea rezervării este trimisă prin e-mail.  

4️⃣ **Filtrare și căutare restaurante**  
   - Posibilitate de căutare după nume sau locație.  
   - Filtrare după tipul bucătăriei (fast-food, fine dining, vegan etc.).  

5️⃣ **Detaliile rezervărilor**  
   - Utilizatorii pot vedea detaliile rezervării (restaurant, dată, oră, număr de locuri).  
   - Oferă opțiunea de anulare a rezervării înainte de ora programată.  
