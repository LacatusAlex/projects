"""hospital URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/4.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from hospital import views
from rest_framework.urlpatterns import format_suffix_patterns
from .views import UserDetailAPI,RegisterUserAPIView
urlpatterns = [
    path('',views.LoginView.as_view()),
    path('admin/', admin.site.urls),
    path('pacients/', views.pacient_list),
    path('pacients/<int:pk>',views.pacient_detail),
    # path("get-details",UserDetailAPI.as_view()),
    path('register',RegisterUserAPIView.as_view()),
    path('login/', views.LoginView.as_view()),
    # path('login2/', views.login_view),
    path('accounts/login/', views.LoginView.as_view()),
    path('profile/', views.ProfileView.as_view()),
    path('logout/', views.logout_view),
]

urlpatterns = format_suffix_patterns(urlpatterns)