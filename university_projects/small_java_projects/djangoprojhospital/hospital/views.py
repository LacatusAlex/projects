
from .models import Pacient
from .serializers import PacientSerializer,LoginSerializer

from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework import status
from rest_framework.permissions import AllowAny
from rest_framework.views import APIView
from rest_framework.response import Response
from .serializers import UserSerializer,RegisterSerializer
from django.contrib.auth.models import User
from rest_framework.authentication import TokenAuthentication
from rest_framework import generics
from rest_framework.authentication import SessionAuthentication, BasicAuthentication
from rest_framework.permissions import IsAuthenticated
from rest_framework.decorators import authentication_classes
from rest_framework.decorators import permission_classes
from rest_framework import permissions
from rest_framework import views
from django.contrib.auth import authenticate, login
from django.contrib.auth.decorators import login_required
from django.contrib.auth import logout
from django.shortcuts import redirect
from django.conf import settings

@api_view(['GET','POST'])
@login_required
def pacient_list(request,format=None):

    print(request.user.username)
    # for permission in request.user.user_permissions:
    print(request.user.user_permissions)
    if request.method == 'GET':
        
        pacients = Pacient.objects.all()
        serializer = PacientSerializer(pacients,many=True)
        return Response(serializer.data)

    if request.method == 'POST':
        print("here") 
        # if request.user.has_perm('hospital.pacient.can_add_pacient'):
        if request.user.username.__contains__('admin'):   
            serializer = PacientSerializer(data= request.data)
            if serializer.is_valid():
                serializer.save()
                return Response(serializer.data, status=status.HTTP_201_CREATED)
            else:
                print(serializer.errors)
        else:
            return Response(status=status.HTTP_203_NON_AUTHORITATIVE_INFORMATION)

    return Response(status=status.HTTP_204_NO_CONTENT)
@api_view(['GET','PUT', 'DELETE'])
@login_required
def pacient_detail(request,pk,format=None):
    try:
        pacient =Pacient.objects.get(pk=pk)
    except Pacient.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    if request.method == 'GET':
        serializer= PacientSerializer(pacient)
        return Response(serializer.data)     
    elif request.method == 'PUT':
        serializer = PacientSerializer(pacient,data = request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors,status=status.HTTP_400_BAD_REQUEST)
    elif request.method == 'DELETE':
        if request.user.username.__contains__('admin'):  
            pacient.delete()
            return Response(status==status.HTTP_204_NO_CONTENT)
        else:
            return Response(status=status.HTTP_203_NON_AUTHORITATIVE_INFORMATION)



# Class based view to Get User Details using Token Authentication
class UserDetailAPI(APIView):
  authentication_classes = (TokenAuthentication,)
  permission_classes = (AllowAny,)
  def get(self,request,*args,**kwargs):
    user = User.objects.get(id=request.user.id)
    serializer = UserSerializer(user)
    return Response(serializer.data)

#Class based view to register user
class RegisterUserAPIView(generics.CreateAPIView):
  permission_classes = (AllowAny,)
  serializer_class = RegisterSerializer
  

class LoginView(generics.CreateAPIView):
    # This view should be accessible also for unauthenticated users.
    permission_classes = (permissions.AllowAny,)
    serializer_class = LoginSerializer
    
    def post(self, request, format=None):
        serializer = LoginSerializer(data=self.request.data,
            context={ 'request': self.request })
        serializer.is_valid(raise_exception=True)
        user = serializer.validated_data['user']
        
        login(request, user)
        return redirect('%s?next=%s' % ("/pacients", request.path))
    

class ProfileView(generics.RetrieveAPIView):
    serializer_class = UserSerializer

    def get_object(self):
        return self.request.user
    
def logout_view(request):
    logout(request)
    return redirect('%s?next=%s' % ("/login", request.path))

def login_view(request):
    username = request.POST['username']
    password = request.POST['password']
    
    user = authenticate(request, username=username, password=password)
    if user is not None:
        login(request, user)
        return redirect('%s?next=%s' % ("/pacients", request.path))
    else:
        return Response(status==status.HTTP_400_BAD_REQUEST)

