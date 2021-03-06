package edu.aucegypt.learningcentershub.ui.user.homescreen;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import edu.aucegypt.learningcentershub.R;
import edu.aucegypt.learningcentershub.service.data.Category;
import edu.aucegypt.learningcentershub.service.data.Course;
import edu.aucegypt.learningcentershub.service.data.LearningCenter;
import edu.aucegypt.learningcentershub.ui.user.categoriesscreen.RecyclerViewAdapter;
import edu.aucegypt.learningcentershub.ui.user.courseslistscreen.CoursesAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;
import static edu.aucegypt.learningcentershub.service.network.APIcall.url;

public class main_frag extends Fragment implements RecyclerViewAdapter.RecyclerViewListner, View.OnClickListener {
    CategoryHomePageAdapter adapter;
    LearningCenterAdapter adapter_1;
    CoursesAdapter adapter_2;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView_1;
    private RecyclerView recyclerView_2;

    String[] Category;
    String[] Locations;
    String[] LearningCenters;
    String[] Courses;
    String[] Courses_learningCenter;
    String[] Courses_Price;
    int[] coursesIcon = {R.drawable.science, R.drawable.programming, R.drawable.engineering, R.drawable.language};

    private categoriesOnClickListener listener;
    private learningCenterOnClickListener listener2;
    private courseOnClickListener listener3;
    TextView seeAllCategories;
    TextView seeAllLearningCenters;
    TextView seeAllCourses;

    TextView courses;

    public interface categoriesOnClickListener
    {
         void onCategoriesListener();
    }

    public interface learningCenterOnClickListener
    {
        void onLearningCenterListener();
    }

    public interface courseOnClickListener
    {
        void onCourseListener();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences prefs = this.getActivity().getSharedPreferences("login_shared_preference", MODE_PRIVATE);
        Boolean status = prefs.getBoolean("status", false);
        int uid = prefs.getInt("uid", 0); //0 is the default value.
        View view = inflater.inflate(R.layout.main_frag, container, false);

        Category = getResources().getStringArray(R.array.category_4);
        LearningCenters = getResources().getStringArray(R.array.learningcenters_4);
        Courses = getResources().getStringArray(R.array.courses_4);
        Courses_learningCenter = getResources().getStringArray(R.array.courses_learningcenter_4);
        Courses_Price = getResources().getStringArray(R.array.courses_price_4);
        Locations = getResources().getStringArray(R.array.locations_4);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_id);
        recyclerView.setLayoutManager(layoutManager);

        courses = (TextView) view.findViewById(R.id.courses);


        String url_api = url + "myroute/getCategories";



        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(url_api)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();

                    Type catListType = new TypeToken<ArrayList<edu.aucegypt.learningcentershub.service.data.Category>>(){}.getType();

                    ArrayList<Category> categoryArrayList = gson.fromJson(response.body().string(), catListType);
                    ArrayList<Category> categoryArrayList_4 = new ArrayList<Category>(4);
                    for(int i = 0; i < 4; i++) {
                        categoryArrayList_4.add(categoryArrayList.get(i));
                    }
                    adapter = new CategoryHomePageAdapter(getContext(), categoryArrayList_4);


                    getActivity().runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView.setAdapter(adapter);
                                }
                            }
                    );



                }

            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
        });


        LinearLayoutManager layoutManager_1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_1 = (RecyclerView) view.findViewById(R.id.recyclerview_id_1);
        recyclerView_1.setLayoutManager(layoutManager_1);


        String url_api_1 = url + "myroute/getLearningCenters";

        OkHttpClient client_1 = new OkHttpClient();

        final Request request_1 = new Request.Builder()
                .url(url_api_1)
                .build();

        client_1.newCall(request_1).enqueue(new Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();

                    Type catListType = new TypeToken<ArrayList<LearningCenter>>(){}.getType();

                    ArrayList<LearningCenter> LearningCentersArrayList = gson.fromJson(response.body().string(), catListType);
                    ArrayList<LearningCenter> LearningCentersArrayList_4 = new ArrayList<LearningCenter>(4);
                    for(int i = 0; i < 4; i++) {
                        LearningCentersArrayList_4.add(LearningCentersArrayList.get(i));
                    }
                    adapter_1 = new LearningCenterAdapter(getContext(), LearningCentersArrayList_4);


                    getActivity().runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView_1.setAdapter(adapter_1);
                                }
                            }
                    );



                }

            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
        });






if (status) {
    LinearLayoutManager layoutManager_2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    recyclerView_2 = (RecyclerView) view.findViewById(R.id.recyclerview_id_2);
    recyclerView_2.setLayoutManager(layoutManager_2);

    courses.setText("Recommended Courses");


    String url_api_2 = url + "myroute/getRecommendedCourses";
    url_api_2 = url_api_2 + "?id=" + Integer.toString(uid);

    OkHttpClient client_2 = new OkHttpClient();

    final Request request_2 = new Request.Builder()
            .url(url_api_2)
            .build();

    client_2.newCall(request_2).enqueue(new Callback() {

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            if (response.isSuccessful()) {
                Gson gson = new Gson();

                Type catListType = new TypeToken<ArrayList<Course>>() {
                }.getType();

                ArrayList<Course> CoursesArrayList = gson.fromJson(response.body().string(), catListType);
                adapter_2 = new CoursesAdapter(getContext(), CoursesArrayList);


                getActivity().runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                recyclerView_2.setAdapter(adapter_2);
                            }
                        }
                );


            }

        }

        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {

        }
    });
}else {
    LinearLayoutManager layoutManager_2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    recyclerView_2 = (RecyclerView) view.findViewById(R.id.recyclerview_id_2);
    recyclerView_2.setLayoutManager(layoutManager_2);

    courses.setText("Courses");


    String url_api_2 = url + "myroute/getCourses";
   // url_api_2 = url_api_2 + "?id=" + Integer.toString(uid);

    OkHttpClient client_2 = new OkHttpClient();

    final Request request_2 = new Request.Builder()
            .url(url_api_2)
            .build();

    client_2.newCall(request_2).enqueue(new Callback() {

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            if (response.isSuccessful()) {
                Gson gson = new Gson();

                Type catListType = new TypeToken<ArrayList<Course>>() {
                }.getType();

                ArrayList<Course> CoursesArrayList = gson.fromJson(response.body().string(), catListType);
                adapter_2 = new CoursesAdapter(getContext(), CoursesArrayList);


                getActivity().runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                recyclerView_2.setAdapter(adapter_2);
                            }
                        }
                );


            }

        }

        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {

        }
    });
}
        //view = inflater.inflate(R.layout.activity_categories, container, false);
        seeAllCategories = (TextView) view.findViewById(R.id.seeallcategories);
        seeAllCategories.setOnClickListener(this);
        //view = inflater.inflate(R.layout.activity_categories, container, false);
        seeAllLearningCenters = (TextView) view.findViewById(R.id.seealllearningcenters);
        seeAllLearningCenters.setOnClickListener(this);
        //view = inflater.inflate(R.layout.activity_categories, container, false);
        seeAllCourses = (TextView) view.findViewById(R.id.coursesseeall);
        seeAllCourses.setOnClickListener(this);



        return view;
    }

    public void onAttach(Context context){
        super.onAttach(context);

        if(context instanceof categoriesOnClickListener){
            listener = (categoriesOnClickListener) context;
        }else {
            throw  new ClassCastException(context.toString()
                    + " must implement categoriesOnClickListener.");
        }
        if(context instanceof learningCenterOnClickListener){
            listener2 = (learningCenterOnClickListener) context;
        }else {
            throw new ClassCastException(context.toString()
                    + " must implement learningCenterOnClickListener.");
        }
        if(context instanceof courseOnClickListener){
            listener3 = (courseOnClickListener) context;
        }else {
            throw new ClassCastException(context.toString()
                    + " must implement courseOnClickListener.");
        }

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.seeallcategories:
                listener.onCategoriesListener();
                break;
            case R.id.seealllearningcenters:
                listener2.onLearningCenterListener();
                break;
            case R.id.coursesseeall:
                listener3.onCourseListener();
                break;
            default:
                break;
        }

    }




    @Override
    public void RecyclerViewClick(int position) {

    }
}