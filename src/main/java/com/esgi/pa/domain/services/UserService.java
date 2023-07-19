package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service de gestion des utilisateurs
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAdapter userAdapter;

    /**
     * Récupère la liste des utilisateur dont le nom contient la recherche
     *
     * @param id   id numérique ?
     * @param name input de la recherche
     * @return liste des utilisateurs contenant l'input
     */
    public List<User> getByNameAndId(Long id, String name) {
        return userAdapter.findByName(id, name);
    }

    /**
     * Récupère un utilisateur par son id
     *
     * @param id id numérique de l'utilsateur
     * @return utilisateur recherché
     * @throws TechnicalNotFoundException si l'utilisateur n'est pas trouvé
     */
    public User getById(Long id) throws TechnicalNotFoundException {
        return userAdapter.findById(id)
            .orElseThrow(
                () -> new TechnicalNotFoundException(HttpStatus.NOT_FOUND, "No user found with following id : " + id));
    }

    /**
     * Récupère les lobbies d'un utilisateur
     *
     * @param user utilisateur dont on cherche les lobbies
     * @return liste des lobbies de l'utilisateur
     */
    public List<Lobby> getLobbiesByUser(User user) {
        return user.getParticipatingLobbies();
    }

    /**
     * Persiste un utilisateur
     *
     * @param user utilisateur à sauvegarder
     * @return l'utilisateur persité
     */
    public User save(User user) {
        return userAdapter.save(user);
    }
}
