package org.FIS2021.services;

import org.FIS2021.exceptions.PlantNotFoundException;
import org.FIS2021.exceptions.UserNotFoundException;
import org.FIS2021.models.Plant;
import org.FIS2021.models.User;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import java.util.List;

import java.util.ArrayList;

import static org.FIS2021.services.FileSystemService.getPathToFile;


public class PlantService {

    private static ObjectRepository<Plant> PendingPlantsRepository;
    private static ObjectRepository<Plant> AcceptedPlantsRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("pendingPlants.db").toFile())
                .openOrCreate("test", "test");
        PendingPlantsRepository = database.getRepository(Plant.class);
        Nitrite database2 = Nitrite.builder()
                .filePath(getPathToFile("acceptedPlants.db").toFile())
                .openOrCreate("test", "test");
        AcceptedPlantsRepository = database2.getRepository(Plant.class);
    }
    public static void updatePendingPlants(Plant p){
        PendingPlantsRepository.update(p);
    }

    public static void providerAddPlant(Plant planta) {
        PendingPlantsRepository.insert(planta);
    }
    public static void managerAddPlant(Plant planta) {
        if(AcceptedPlantsRepository.find(ObjectFilters.eq("nume", planta.getNume())).size() == 0) {
            AcceptedPlantsRepository.insert(planta);
        }
        else {

            Cursor<Plant> cursor  = AcceptedPlantsRepository.find(ObjectFilters.eq("nume", planta.getNume()));
            Plant p= cursor.firstOrDefault();
            p.setCantitate(p.getCantitate()+ planta.getCantitate());
            AcceptedPlantsRepository.update(p);
        }
    }
    public static void managerRemovePlant(Plant planta){
       PendingPlantsRepository.remove(planta);
    }

    public static Plant getPlantProvider(String nume) throws PlantNotFoundException {
        Cursor<Plant> cursor = PendingPlantsRepository.find(ObjectFilters.eq("nume", nume));
        for (Plant p : cursor) {
            return p;
        }
        throw new PlantNotFoundException(nume);
    }
    public static ArrayList<Plant> getAllPlantsProvider() {

        ArrayList<Plant> result = new ArrayList<>();
        for(Plant p :  PendingPlantsRepository.find()){
            result.add(p);
        }

        return result;
    }
    public static ArrayList<Plant> getAllPlantsShop() {

        ArrayList<Plant> result = new ArrayList<>();
        for(Plant p : AcceptedPlantsRepository.find()){
            result.add(p);
        }

        return result;
    }

    public static List PlantList() {
        List<Plant> plant = new ArrayList<>();
        for (Plant p : PendingPlantsRepository.find()) {
            plant.add(p);
        }
        return plant;
    }

    public static void initService() {
        PendingPlantsRepository = Comandaservice.getDatabase().getRepository(Plant.class);
    }



}